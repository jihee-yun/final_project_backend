package com.kh.finalProject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class KAKAOService {
    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    @Value("${kakao.grant-type}")
    private String authGrantType;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    public String requestKakaoToken(Map<String, String> kakaoAuth) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("code", kakaoAuth.get("authorizationCode"));

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = responseEntity.getBody();
            String token = (String) responseBody.get("access_token");
            return token;
        } else {
            log.error("카카오 토큰 요청에 실패했습니다.");
            // 에러 상황을 처리하거나 예외를 throw할 수 있습니다.
            return null;
        }
    }
    public String requestKakaoUserInfo(String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                requestEntity,
                Map.class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = responseEntity.getBody();
            // 사용자 정보 처리 로직 추가
            return "사용자 정보";
        } else {
            log.error("카카오 사용자 정보 요청에 실패했습니다.");
            // 에러 상황을 처리하거나 예외를 throw할 수 있습니다.
            return null;
        }
    }
}
