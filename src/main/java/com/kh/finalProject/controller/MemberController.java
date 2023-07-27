package com.kh.finalProject.controller;

import com.kh.finalProject.dto.*;
import com.kh.finalProject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {
    private final MemberService memberService;

    // 사업자 회원 회원가입
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(memberService.signup(requestDto));
    }
    // 사업자 회원 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(memberService.login(requestDto));
    }

    // 아이디 찾기
    @PostMapping("/findId")
    public ResponseEntity<String> findId(@RequestBody Map<String, String> findIdData) {
        String name = findIdData.get("name");
        String email = findIdData.get("email");
        String memberDto = memberService.findId(name, email);
        if (memberDto == null) {
            // 아이디를 찾지 못한 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        // 아이디를 찾은 경우
        return ResponseEntity.ok(memberDto);
    }

//    // 비밀번호 찾기
    @PostMapping("/findPw")
    public ResponseEntity<Boolean> findPw(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");
        String memberId = requestData.get("memberId");
        String name = requestData.get("name");
        Boolean result = memberService.findPw(email, memberId, name);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 새 비밀번호 변경
//    @PostMapping("/changePw")
//    public ResponseEntity<Boolean> changePw(@RequestBody PasswordDto passwordDto) {
//        String email = passwordDto.getEmail();
//        String newPassword = passwordDto.getNewPassword();
//        Boolean result = memberService.changePw(email, newPassword);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    // 회원 아이디로 회원 번호 조회
//    @PostMapping("/numget")
//    public ResponseEntity<List<MemberDto>> memberNum(@RequestBody MemberDto memberIdData) {
//        String memberId = memberIdData.getMemberId();
//        List<MemberDto> list = memberService.getMemberNumById(memberId);
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }

    // 멤버 정보 조회
    @GetMapping("/myinfo")
    public ResponseEntity<List<MemberDto>> infoList(@RequestParam Long memberNum) {
        List<MemberDto> list = memberService.getMemberInfoByNum(memberNum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}