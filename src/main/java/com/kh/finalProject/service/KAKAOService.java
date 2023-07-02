package com.kh.finalProject.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.finalProject.dto.UserDto;
import com.kh.finalProject.entity.User;
import com.kh.finalProject.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import javax.servlet.http.HttpSession;

import java.util.Map;
import java.util.Optional;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class KAKAOService {
    private final HttpSession session;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.grant-type}")
    private String grantType;

    // 카카오 서버에 access token 요청
    public String requestAccessToken(String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", grantType);
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("redirect_uri", redirectUri);
        formData.add("code", code);

        Map<String, String> tokenResponse = webClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .block();

        // objectMapper.writeValueAsString(tokenResponse)를 사용하여 tokenResponse 맵을 JSON 형식의 문자열로 변환
        try {
            return objectMapper.writeValueAsString(tokenResponse);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert token response to JSON: {}", e.getMessage());
            throw new RuntimeException("Failed to convert token response to JSON");
        }
    }


    // 카카오에게 전달된 이메일 정보로 가입 정보를 확인하는 메소드
    public boolean checkRegistrationByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.isPresent();
    }

    // 생성된 JWT를 DTO에 담은 뒤 JSON 객체화하는 메소드
    public String generateJWT(UserDto userDto) {
        try {
            String userJson = objectMapper.writeValueAsString(userDto);
            return Jwts.builder()
                    .setSubject(userDto.getEmail())
                    .claim("user", userJson)
                    .signWith(SignatureAlgorithm.HS256, clientSecret.getBytes())
                    .compact();
        } catch (JsonProcessingException e) {
            log.error("Failed to generate JWT: {}", e.getMessage());
            throw new RuntimeException("Failed to generate JWT");
        }
    }

    // 카카오 서버에 token이 담긴 entity를 요청하고 받아와서 session에 저장
    public void saveTokenToSession(String accessToken) {
        String tokenInfo = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        session.setAttribute("kakaoTokenInfo", tokenInfo);
    }

    // access token을 추출하여 반환하는 메소드
    public String extractAccessToken(String tokenInfo) {
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(tokenInfo);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse token info: {}", e.getMessage());
            throw new RuntimeException("Failed to parse token info");
        }

        return jsonNode.path("access_token").asText();
    }

    // 추출한 access token과 refresh token을 session에 저장
    public void saveTokensToSession(String accessToken, String refreshToken) {
        session.setAttribute("accessToken", accessToken);
        session.setAttribute("refreshToken", refreshToken);
    }

    // access token을 이용해 유저 정보를 받아오는 메소드
    public UserDto getUserInfo(String accessToken) {
        String userInfo = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        UserDto userDto;
        try {
            userDto = objectMapper.readValue(userInfo, UserDto.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse user info: {}", e.getMessage());
            throw new RuntimeException("Failed to parse userinfo");
        }

        return userDto;
    }
}