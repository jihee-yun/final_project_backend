package com.kh.finalProject.controller;

import com.kh.finalProject.dto.MemberDto;
import com.kh.finalProject.dto.UserDto;
import com.kh.finalProject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/info")
@CrossOrigin(origins = "http://localhost:3000")
public class InfoController {
    private final MemberService memberService;

    // 회원정보 수정에 필요한 회원 정보 대부분 조회
    @GetMapping("/memberinfo")
    public ResponseEntity<List<MemberDto>> userInfoByNum(@RequestParam Long membernum) {
        List<MemberDto> list = memberService.getMemberInfoByNum(membernum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 한 줄 소개 수정
    @PostMapping("/introchange")
    public ResponseEntity<Boolean> userIntroChange(@RequestBody MemberDto introData) {
        Long memberNum = introData.getMemberNum();
        String intro = introData.getIntro();
        Boolean result = memberService.changeMemberIntroByNum(memberNum, intro);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 핸드폰 번호 수정
    @PostMapping("/phonechange")
    public ResponseEntity<Boolean> userPhoneChange(@RequestBody MemberDto phoneData) {
        Long memberNum = phoneData.getMemberNum();
        String phone = phoneData.getPhone();
        Boolean result = memberService.changeMemberPhoneByNum(memberNum, phone);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
