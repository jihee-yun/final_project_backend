package com.kh.finalProject.controller;

import com.kh.finalProject.jwt.JwtProvider;
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

    public String accessToken(String userId) throws Exception{
        String accessToken = jwtProvider.generateAccessToken(userId);
        return accessToken;
    }

    public String refreshTokenCreate(String userId) throws Exception {
        String refreshToken = jwtProvider.generateAccessToken(userId);
        return refreshToken;
    }
}
