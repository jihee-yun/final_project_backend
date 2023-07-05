package com.kh.finalProject.controller;


import com.kh.finalProject.dto.*;
import com.kh.finalProject.service.AccessTokenService;
import com.kh.finalProject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthService authService;
    private final AccessTokenService accessTokenService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> userSignUp(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(authService.userSignUp(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> userLogin(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(authService.userLogin(userRequestDto));
    }

    // 사업자 회원 회원가입
    @PostMapping("/member/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }
    // 사업자 회원 로그인
    @PostMapping("/member/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }


    // 리프레시 토큰 받아서 엑세스 토큰 재발급
    @PostMapping("/token")
    public ResponseEntity<AccessTokenDto> createNewAccessToken(@RequestBody RefreshTokenDto requestDto) {
        String newAccessToken = accessTokenService.createNewAccessToken(requestDto.getRefreshToken());
        AccessTokenDto responseDto = new AccessTokenDto(newAccessToken);
        return ResponseEntity.ok(responseDto);
    }

}
