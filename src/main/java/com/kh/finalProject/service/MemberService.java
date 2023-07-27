package com.kh.finalProject.service;

import com.kh.finalProject.constant.Authority;
import com.kh.finalProject.constant.Existence;
import com.kh.finalProject.dto.*;
import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.Report;
import com.kh.finalProject.jwt.TokenProvider;
import com.kh.finalProject.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.*;
import java.time.LocalDate;
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
    private final ReviewRepository reviewRepository;
    private final MyChallengeRepository myChallengeRepository;
    private final GuildMemberRepository guildMemberRepository;
    private final PointRepository pointRepository;
    private final PaymentRepository paymentRepository;
    private final ReportRepository reportRepository;
    private final MailService mailService;
    private static Map<String, String> verificationCodes = new HashMap<>();

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
    public Boolean findPw(String email, String memberId, String name) {
        Optional<Member> memberOptional = memberRepository.findByEmailAndMemberIdAndName(email, memberId, name);
        if (memberOptional.isEmpty()) {
            System.out.println("회원 정보가 없습니다: email=" + email + ", memberId : " + memberId + ", name=" + name);
            return false; // 회원 정보가 없을 경우 false 반환
        }

        Member member = memberOptional.get();
        memberRepository.save(member);

        // 인증 번호를 이메일로 전송
        sendAuthNumberEmail(email);

        return true; // 인증 번호 전송 성공 시 true 반환
    }

    private void sendAuthNumberEmail(String email) {
        MimeMessage message = mailService.CreateMail(email);

        try {
            message.setSubject("비밀번호 재설정 인증 번호");
            mailService.confirm(email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }



    // 새 비밀번호
//    public Boolean changePw(String email, String newPassword) {
//        Optional<Member> memberOptional = memberRepository.findByEmail(email);
//
//        // 이메일에 해당하는 회원을 찾았는지 확인
//        if (memberOptional.isPresent()) {
//            Member member = memberOptional.get();
//            // 회원을 찾았을 경우에만 비밀번호 변경
//            member.setPassword(newPassword);
//            memberRepository.save(member);
//            return true; // 비밀번호 변경 성공
//        }
//
//        return false; // 회원을 찾지 못한 경우, 비밀번호 변경 실패
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

    // 회원 번호로 대시보드용 전체 정보 조회
    public List<MemberAllInfoDto> getMemberAllInfoByNum(Long memberNum) {
        Optional<Member> memberOptional = memberRepository.findByMemberNum(memberNum);
        List<MemberAllInfoDto> memberAllInfoDtoList = new ArrayList<>();
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            MemberAllInfoDto memberAllInfoDto = new MemberAllInfoDto();
            memberAllInfoDto.setMemberNum(member.getMemberNum());
            List<String> reviewContents = reviewRepository.findReviewContentByUserNum(memberNum);
            if (reviewContents.size() > 6) {
                reviewContents = reviewContents.subList(0, 6);
            }
            memberAllInfoDto.setReviewContents(reviewContents);
            List<String> guildNames = guildMemberRepository.findGuildNamesByMemberNum(memberNum);
            if (guildNames.size() > 6) {
                guildNames = guildNames.subList(0, 6);
            }
            memberAllInfoDto.setGuildNames(guildNames);
            List<String> challengeNames = myChallengeRepository.findChallengeNamesByMemberNum(memberNum);
            if (challengeNames.size() > 6) {
                challengeNames = challengeNames.subList(0, 6);
            }
            memberAllInfoDto.setChallengeNames(challengeNames);
            List<String> pointTypes = pointRepository.findPointTypeByMember(member);
            if (pointTypes.size() > 6) {
                pointTypes = pointTypes.subList(0, 6);
            }
            memberAllInfoDto.setPointTypes(pointTypes);
            List<String> paymentTypes = paymentRepository.findPaymentTypeByMember(member);
            if (paymentTypes.size() > 6) {
                paymentTypes = paymentTypes.subList(0, 6);
            }
            memberAllInfoDto.setPaymentTypes(paymentTypes);
            List<String> reportTitles = reportRepository.findTitleByUserId(member.getMemberId());
            if (reportTitles.size() > 6) {
                reportTitles = reportTitles.subList(0, 6);
            }
            memberAllInfoDto.setReportTitles(reportTitles);

            memberAllInfoDtoList.add(memberAllInfoDto);
        }
        return memberAllInfoDtoList;
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

    // 임시 메소드 : 회원 번호와 기간으로 문의,신고 조회
    public List<ReportDto> getReportListByNumAndDate(Long memberNum, LocalDate startDate, LocalDate endDate) {
        Member member = new Member();
        member.setMemberNum(memberNum);
        Optional<Member> memberOpt = memberRepository.findByMemberNum(memberNum);
        if (memberOpt.isPresent()) {
            Member memberFromOpt = memberOpt.get();
            List<Report> reportList = reportRepository.findByUserIdAndReportDateBetween(memberFromOpt.getMemberId(), startDate, endDate);
            List<ReportDto> reportDtoList = new ArrayList<>();
            for (Report report : reportList) {
                ReportDto reportDto = new ReportDto();
                reportDto.setReportNum(report.getReportNum());
                reportDto.setTitle(report.getTitle());
                reportDto.setReportDate(report.getReportDate());
                reportDtoList.add(reportDto);
            }
            return reportDtoList;
        }
        return new ArrayList<>();
    }
}