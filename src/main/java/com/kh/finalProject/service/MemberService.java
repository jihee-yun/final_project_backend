package com.kh.finalProject.service;

import com.kh.finalProject.constant.Authority;
import com.kh.finalProject.dto.MemberDto;
import com.kh.finalProject.dto.MemberRequestDto;
import com.kh.finalProject.dto.MemberResponseDto;
import com.kh.finalProject.dto.TokenDto;
import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.Point;
import com.kh.finalProject.jwt.TokenProvider;
import com.kh.finalProject.repository.MemberRepository;
import com.kh.finalProject.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final PointRepository pointRepository;

    // 사업자 회원 가입
    public MemberResponseDto signup(MemberRequestDto requestDto) {
        int points = requestDto.getPoints();
        if (memberRepository.existsByMemberId(requestDto.getMemberId())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        Member member = requestDto.toMember(passwordEncoder);
        memberRepository.save(member);

        Point point = new Point();
        point.setMember(member);
        point.setTotalPoint(points);
        pointRepository.save(point);

        return MemberResponseDto.of(member);
    }

    // 사업자 회원 로그인
    public TokenDto login(MemberRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateToken(authentication);
    }

//    // 사업자 회원 아이디로 사업자 회원 번호 조회
//    public List<MemberDto> getMemberNumById(String memberId) {
//        Optional<Member> memberOptional = memberRepository.findByMemberId(memberId);
//        List<MemberDto> memberDtoList = new ArrayList<>();
//        if (memberOptional.isPresent()) {
//            Member member = memberOptional.get();
//            MemberDto memberDto = new MemberDto();
//            memberDto.setMemberNum(member.getMemberNum());
//            memberDtoList.add(memberDto);
//        }
//        return memberDtoList;
//    }
    // 회원 아이디로 회원 번호 조회
    public Long getMemberNumByMemberId(String memberId) {
        Optional<Member> member = memberRepository.findByMemberId(memberId);
        if(member.isPresent()) {
            Member member1 = member.get();
            return member1.getMemberNum();
        }
        return null;
    }
    // 회원 아이디로 회원 이름 조회
    public String getMemberNameByMemberId(String memberId) {
        Optional<Member> member = memberRepository.findByMemberId(memberId);
        if(member.isPresent()) {
            Member member1 = member.get();
            return member1.getName();
        }
        return null;
    }
    // 회원 아이디로 회원 종류(권한) 조회
    public Authority getMemberAuthorityByMemberId(String memberId) {
        Optional<Member> member = memberRepository.findByMemberId(memberId);
        if(member.isPresent()) {
            Member member1 = member.get();
            return member1.getAuthority();
        }
        return null;
    }

    // 회원 번호로 정보 조회
    public List<MemberDto> getMemberInfoByNum(Long memberNum) {
        Optional<Member> memberOptional = memberRepository.findByMemberNum(memberNum);
        List<MemberDto> memberDtoList = new ArrayList<>();
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            MemberDto memberDto = new MemberDto();
            memberDto.setMemberId(member.getMemberId());
            memberDto.setPhone(member.getPhone());
            memberDto.setEmail(member.getEmail());
            memberDto.setBirthday(member.getBirthday());
            memberDto.setGender(member.getGender());
            memberDto.setSignUpDay(member.getSignUpDay());
            memberDto.setFollowingId(member.getFollowingId());
            memberDto.setFollowedId(member.getFollowedId());
            memberDto.setProfileImgUrl(member.getProfileImgUrl());
            memberDto.setIntro(member.getIntro());
            memberDto.setExistence(member.getExistence());
            memberDto.setAuthority(member.getAuthority());
            memberDtoList.add(memberDto);
        }
        return memberDtoList;
    }

    // 회원 한 줄 소개 변경
    public Boolean changeMemberIntroByNum(Long memberNum, String intro) {
        Optional<Member> memberOptional = memberRepository.findByMemberNum(memberNum);
        AtomicBoolean result = new AtomicBoolean(false);
        memberOptional.ifPresent(member -> {
            member.setIntro(intro);
            memberRepository.save(member);
            result.set(true);
        });
        return result.get();
    }
    // 회원 전화 번호 변경
    public Boolean changeMemberPhoneByNum(Long memberNum, String phone) {
        Optional<Member> memberOptional = memberRepository.findByMemberNum(memberNum);
        AtomicBoolean result = new AtomicBoolean(false);
        memberOptional.ifPresent(member -> {
            member.setPhone(phone);
            memberRepository.save(member);
            result.set(true);
        });
        return result.get();
    }
}


