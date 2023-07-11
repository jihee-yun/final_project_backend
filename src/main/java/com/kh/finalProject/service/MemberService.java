package com.kh.finalProject.service;

import com.kh.finalProject.dto.MemberDto;
import com.kh.finalProject.dto.MemberRequestDto;
import com.kh.finalProject.dto.MemberResponseDto;
import com.kh.finalProject.dto.TokenDto;
import com.kh.finalProject.entity.Member;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateToken(authentication);
    }

    // 사업자 회원 아이디로 사업자 회원 번호 조회
    public List<MemberDto> getMemberNumById(String memberId) {
        Optional<Member> memberOptional = memberRepository.findByMemberId(memberId);
        List<MemberDto> memberDtoList = new ArrayList<>();
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            MemberDto memberDto = new MemberDto();
            memberDto.setMemberNum(member.getMemberNum());
            memberDtoList.add(memberDto);
        }
        return memberDtoList;
    }

    public Long getMemberNumByMemberId(String memberId) {
        Optional<Member> member = memberRepository.findByMemberId(memberId);
        if(member.isPresent()) {
            Member member1 = member.get();
            return member1.getMemberNum();
        }
        return null;
    }
}


