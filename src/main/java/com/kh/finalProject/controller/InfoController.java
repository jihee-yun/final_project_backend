package com.kh.finalProject.controller;

import com.kh.finalProject.dto.*;
import com.kh.finalProject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/info")
@CrossOrigin(origins = "http://localhost:3000")
public class InfoController {
    private final MemberService memberService;

    // 대시보드용 회원 정보 전체 조회
    @GetMapping("/allinfo")
    public ResponseEntity<List<MemberAllInfoDto>> memberAllInfoByNum(@RequestParam Long membernum) {
        List<MemberAllInfoDto> list = memberService.getMemberAllInfoByNum(membernum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 회원정보 수정에 필요한 회원 정보 대부분 조회
    @GetMapping("/memberinfo")
    public ResponseEntity<List<MemberDto>> memberInfoByNum(@RequestParam Long membernum) {
        List<MemberDto> list = memberService.getMemberInfoByNum(membernum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 비밀번호 수정
    @PostMapping("/passwordchange")
    public ResponseEntity<Boolean> memberPasswordChange(@RequestBody PasswordDto passwordData) {
        Long memberNum = passwordData.getMemberNum();
        String password = passwordData.getPassword();
        String newPassword = passwordData.getNewPassword();
        Boolean result = memberService.changeMemberPasswordByNum(memberNum, password, newPassword);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 한 줄 소개 수정
    @PostMapping("/introchange")
    public ResponseEntity<Boolean> memberIntroChange(@RequestBody MemberDto introData) {
        Long memberNum = introData.getMemberNum();
        String intro = introData.getIntro();
        Boolean result = memberService.changeMemberIntroByNum(memberNum, intro);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    // 핸드폰 번호 수정
    @PostMapping("/phonechange")
    public ResponseEntity<Boolean> memberPhoneChange(@RequestBody MemberDto phoneData) {
        Long memberNum = phoneData.getMemberNum();
        String phone = phoneData.getPhone();
        Boolean result = memberService.changeMemberPhoneByNum(memberNum, phone);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    // 이메일 수정
    @PostMapping("/emailchange")
    public ResponseEntity<Boolean> memberEmailChange(@RequestBody MemberDto emailData) {
        Long memberNum = emailData.getMemberNum();
        String email = emailData.getEmail();
        Boolean result = memberService.changeMemberEmailByNum(memberNum, email);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    // 회원 탈퇴
    @GetMapping("/memberwithdraw")
    public ResponseEntity<Boolean> memberWithdraw(@RequestParam Long membernum) {
        Boolean result = memberService.memberWithdrawByNum(membernum);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 임시 메소드 : 문의,신고 내역 조회
    @PostMapping("/reportgetbynumdate")
    public ResponseEntity<List<ReportDto>> reportInfoByNum(@RequestBody ReportDateDto checkData) {
        Long memberNum = checkData.getMemberNum();
        LocalDate startDate = checkData.getStartDate();
        LocalDate endDate = checkData.getEndDate();
        List<ReportDto> list = memberService.getReportListByNumAndDate(memberNum, startDate, endDate);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
