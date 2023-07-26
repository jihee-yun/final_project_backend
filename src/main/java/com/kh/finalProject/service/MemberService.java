package com.kh.finalProject.service;

import com.kh.finalProject.constant.Authority;
import com.kh.finalProject.constant.Existence;
import com.kh.finalProject.dto.*;
import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.User;
import com.kh.finalProject.jwt.TokenProvider;
import com.kh.finalProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.security.SecureRandom;
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

    // 사업자 회원 가입
    public MemberResponseDto signup(MemberRequestDto requestDto) {
        if (memberRepository.existsByMemberId(requestDto.getMemberId())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        Member member = requestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    // 사업자 회원 로그인
    public TokenDto login(MemberRequestDto requestDto) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(requestDto.getMemberId());

        if (!optionalMember.isPresent()) {
            throw new RuntimeException("아이디와 비밀번호를 다시 확인해주세요.."); // 예외처리 (멤버가 존재하지 않을 때)
        }
        Member member = optionalMember.get();
        if (member.getExistence() != Existence.Yes) {
            throw new RuntimeException("Member is not active"); // 예외처리 (멤버가 비활성화 상태일 때)
        }
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateToken(authentication);
    }

    // 아이디 찾기
    public String findId(String name, String email) {
        Optional<Member> memberInfo = memberRepository.findByNameAndEmail(name, email);
        System.out.println("정보 : " + name + " " + email + " " + memberInfo);
        if (!memberInfo.isPresent()) {
            System.out.println("아이디를 찾지 못함");
            return null; // 아이디를 찾지 못한 경우 null을 반환하거나 원하는 대응을 수행
        }
        Member member = memberInfo.get();
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberId(member.getMemberId());
        System.out.println("ID 찾기 :" + memberDto.getMemberId());
        String result = member.getMemberId();
        return result;
    }

    // 비밀번호 찾기
//    public Boolean findPw(String name, String email, String phone) {
//        Optional<Member> userOptional = memberRepository.findByNameAndPhoneAndEmail(name, phone, email);
//        AtomicBoolean result = new AtomicBoolean(false);
//        userOptional.ifPresent(member -> {
//            String temporaryPassword = generateTemporaryPassword();
//            member.setPassword(passwordEncoder.encode(temporaryPassword)); // 비밀번호 암호화하여 설정
//            memberRepository.save(member);
//            sendTemporaryPasswordEmail(member.getEmail(), temporaryPassword);
//            result.set(true);
//        });
//        return result.get();
//    }
//
//    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
//    private static final int PASSWORD_LENGTH = 10;
//
//    private String generateTemporaryPassword() {
//        StringBuilder temporaryPassword = new StringBuilder();
//        SecureRandom secureRandom = new SecureRandom();
//
//        for (int i = 0; i < PASSWORD_LENGTH; i++) {
//            int randomIndex = secureRandom.nextInt(CHARACTERS.length());
//            temporaryPassword.append(CHARACTERS.charAt(randomIndex));
//        }
//
//        return temporaryPassword.toString();
//    }
//
//    private void sendTemporaryPasswordEmail(String email, String temporaryPassword) {
//        // 여기서는 간단하게 콘솔에 메시지를 출력하는 것으로 대체합니다.
//        System.out.println("임시 비밀번호를 다음 이메일로 발송합니다 : " + email);
//        System.out.println("임시 비밀번호 : " + temporaryPassword);
//    }

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
            memberDto.setTotalPoint(member.getTotalPoint());
            memberDto.setName(member.getName());
            memberDtoList.add(memberDto);
        }
        return memberDtoList;
    }

    // 회원 비밀번호 변경
    public Boolean changeMemberPasswordByNum(Long memberNum, String password, String newPassword) {
        Optional<Member> memberOptional = memberRepository.findByMemberNum(memberNum);
        AtomicBoolean result = new AtomicBoolean(false);
        memberOptional.ifPresent(member -> {
            if (passwordEncoder.matches(password, member.getPassword())) {
                member.setPassword(passwordEncoder.encode(newPassword));
                memberRepository.save(member);
                result.set(true);
            }
        });
        return result.get();
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
    // 회원 이메일 변경
    public Boolean changeMemberEmailByNum(Long memberNum, String email) {
        Optional<Member> memberOptional = memberRepository.findByMemberNum(memberNum);
        AtomicBoolean result = new AtomicBoolean(false);
        memberOptional.ifPresent(member -> {
            member.setEmail(email);
            memberRepository.save(member);
            result.set(true);
        });
        return result.get();
    }
    // 회원 탈퇴
    public Boolean memberWithdrawByNum(Long memberNum) {
        Optional<Member> memberOptional = memberRepository.findByMemberNum(memberNum);
        AtomicBoolean result = new AtomicBoolean(false);
        memberOptional.ifPresent(member -> {
            member.setExistence(Existence.NO);
            memberRepository.save(member);
            result.set(true);
        });
        return result.get();
    }
}


