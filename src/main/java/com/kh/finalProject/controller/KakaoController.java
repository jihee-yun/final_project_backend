package com.kh.finalProject.controller;

import com.kh.finalProject.dto.KakaoAuthRequestDto;
import com.kh.finalProject.service.KAKAOService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class KakaoController {
    private final KAKAOService kakaoService;

    @PostMapping("/login")
    public ResponseEntity<String> kakaoLogin(@RequestBody KakaoAuthRequestDto kakaoAuth){
        Map<String, String> kakaoAuthMap = new HashMap<>();
        kakaoAuthMap.put("grant_type", "authorization_code");
        kakaoAuthMap.put("client_id", kakaoAuth.getClient_id());
        kakaoAuthMap.put("client_secret", kakaoAuth.getSecretKey());
        kakaoAuthMap.put("redirect_uri", kakaoAuth.getRedirect_uri());
        kakaoAuthMap.put("code", kakaoAuth.getCode());

        String token = kakaoService.requestKakaoToken(kakaoAuthMap);

        return ResponseEntity.ok(token);
    }


    @PostMapping("/userinfo")
    public ResponseEntity<String> getUserInfo(@RequestBody String token) {
        String userInfo = kakaoService.requestKakaoUserInfo(token);
        return ResponseEntity.ok(userInfo);
    }
}
