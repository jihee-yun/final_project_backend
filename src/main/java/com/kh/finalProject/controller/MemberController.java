package com.kh.finalProject.controller;

import com.kh.finalProject.dto.MemberRequestDto;
import com.kh.finalProject.dto.MemberResponseDto;
import com.kh.finalProject.dto.TokenDto;
import com.kh.finalProject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
