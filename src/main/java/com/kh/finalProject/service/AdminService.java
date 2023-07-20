package com.kh.finalProject.service;

import com.kh.finalProject.dto.*;
import com.kh.finalProject.entity.Admin;
import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.User;
import com.kh.finalProject.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final ReportRepository reportRepository;
    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    // 전체 회원 조회
    public List<MemberDto> findAllUserList() {
        List<Member> members = memberRepository.findAll();
        List<MemberDto> memberDtos = new ArrayList<>();
        for (Member member : members) {
            MemberDto memberDto = new MemberDto();
            memberDto.setMemberNum(member.getMemberNum());
            memberDto.setMemberId(member.getMemberId());
            memberDto.setName(member.getName());
            memberDto.setEmail(member.getEmail());
            memberDto.setPhone(member.getPhone());
            memberDto.setGender(member.getGender());
            memberDto.setBirthday(member.getBirthday());
            memberDto.setExistence(member.getExistence());
            memberDto.setAuthority(member.getAuthority());
            memberDto.setTotalPoint(member.getTotalPoint());
            memberDtos.add(memberDto);
        }
        return memberDtos;
    }

    // 관리자 등록
    public AdminResponseDto adminSignUp(AdminRequestDto adminRequestDto) {
        if(adminRepository.existsByAdminId(adminRequestDto.getAdminId())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        Admin admin = adminRequestDto.toAdmin();
        return AdminResponseDto.of(adminRepository.save(admin));
    }

    // 관리자 로그인
    public AdminDto findByAdminIdAndPassWord(String adminId, String password) {
        Optional<Admin> adminOptional = adminRepository.findByAdminIdAndPassword(adminId, password);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            AdminDto adminDto = new AdminDto();
            adminDto.setAdminId(admin.getAdminId());

            // 입력받은 비밀번호를 암호화하여 설정
            String encryptedPassword = passwordEncoder.encode(password);
            adminDto.setPassword(encryptedPassword);

            return adminDto;
        } else {
            // 관리자가 존재하지 않는 경우에 대한 처리를 원하시면 추가하시면 됩니다.
            // 예를 들어, return null 또는 새로운 AdminDto를 만들어서 에러 메시지를 포함시킬 수 있습니다.
            return null;
        }
    }
}
