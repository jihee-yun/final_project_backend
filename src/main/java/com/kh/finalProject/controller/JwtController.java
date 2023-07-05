package com.kh.finalProject.controller;

import com.kh.finalProject.jwt.JwtProvider;
import com.kh.finalProject.jwt.TokenProvider;
import com.kh.finalProject.repository.RefreshTokenRepository;
import com.kh.finalProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class JwtController {
    private final JwtProvider jwtProvider;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public String accessToken(String userId) throws Exception{


        String accessToken = jwtProvider.generateAccessToken(userId);
        return accessToken;
    }

    public String refreshTokenCreate(String userId) throws Exception {
        String refreshToken = jwtProvider.generateRefreshToken(userId);
        return refreshToken;
    }
}
