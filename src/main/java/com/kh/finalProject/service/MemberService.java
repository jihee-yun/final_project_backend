package com.kh.finalProject.service;


import com.kh.finalProject.dto.MemberDto;
import com.kh.finalProject.entity.Member;
import com.kh.finalProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 가입
    public boolean regMember(String userId, String password, String name, String email) {
        Member member = new Member();
        member.setUserId(userId);
        member.setPassword(password);
        member.setName(name);
        member.setEmail(email);
        if(regMemberCheck(member)) {
            memberRepository.save(member);
            return true;
        }
        return false;
    }

    public boolean regMemberCheck(Member member) {
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());
        return findMember.isEmpty();
    }

    // 로그인 체크
    public boolean loginCheck(String userId, String password) {
        Optional<Member> memberInfo = memberRepository.findByUserIdAndPassword(userId, password);
        return memberInfo.isEmpty();
    }

    // 회원 조회
    public List<MemberDto> getMemberList() {
        List<Member> memberInfoList;
        memberInfoList = memberRepository.findAll();

        List<MemberDto> memberDtos = new ArrayList<>();
        for(Member info : memberInfoList) {
            MemberDto memberDto = new MemberDto();
            memberDto.setUserId(info.getUserId());
            memberDto.setPassword(info.getPassword());
            memberDto.setName(info.getName());
            memberDto.setEmail(info.getEmail());
            memberDtos.add(memberDto);
        }
        return memberDtos;
    }
}
