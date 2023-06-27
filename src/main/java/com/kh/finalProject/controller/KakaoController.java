package com.kh.finalProject.controller;

import com.kh.finalProject.service.KAKAOService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KakaoController {
    private final KAKAOService kakaoService;

    @PostMapping("")
    public ResponseEntity<?> kakoLogin(@RequestBody Map<String, String> kakaoAuth) {
        String token = kakaoService.requestKakaoToken(kakaoAuth);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(@RequestBody String token) {
        String userInfo = kakaoService.requestKakaoUserInfo(token);
        return ResponseEntity.ok(userInfo);
    }
}
