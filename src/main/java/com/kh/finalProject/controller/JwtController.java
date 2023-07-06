package com.kh.finalProject.controller;

import com.kh.finalProject.dto.AccessTokenDto;
import com.kh.finalProject.dto.RefreshTokenDto;
import com.kh.finalProject.jwt.JwtProvider;
import com.kh.finalProject.jwt.TokenProvider;
import com.kh.finalProject.repository.RefreshTokenRepository;
import com.kh.finalProject.repository.UserRepository;
import com.kh.finalProject.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/token")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class JwtController {
    private final JwtProvider jwtProvider;
    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @PostMapping("/regen-acc")
    public ResponseEntity<AccessTokenDto> createNewAccessToken(@RequestBody RefreshTokenDto requestDto) {
        String newAccessToken = authService.createNewAccessToken(requestDto.getRefreshToken());
        AccessTokenDto responseDto = new AccessTokenDto(newAccessToken);
        return ResponseEntity.ok(responseDto);
    }


    // 처리 예정
//    public String accessToken(String userId) throws Exception{
//        String accessToken = jwtProvider.generateAccessToken(userId);
//        return accessToken;
//    }
//    public String refreshTokenCreate(String userId) throws Exception {
//        String refreshToken = jwtProvider.generateRefreshToken(userId);
//        return refreshToken;
//    }
}
