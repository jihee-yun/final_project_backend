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
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }

    @PostMapping("/token")
    public ResponseEntity<AccessTokenDto> createNewAccessToken(@RequestBody RefreshTokenDto requestDto) {
        String newAccessToken = accessTokenService.createNewAccessToken(requestDto.getRefreshToken());
        AccessTokenDto responseDto = new AccessTokenDto(newAccessToken);
        return ResponseEntity.ok(responseDto);
    }


}

