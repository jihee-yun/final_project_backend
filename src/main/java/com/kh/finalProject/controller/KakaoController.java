package com.kh.finalProject.controller;



import com.kh.finalProject.service.KAKAOService;
import com.kh.finalProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;

@Slf4j
@RequestMapping("/koauth/login")
@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class KakaoController {
    String email = "taehoon1999@nate.com";
    private final KAKAOService kakaoService;
    private final UserService userService;

    // authorization code를 받아옴
    @GetMapping("/kakao")
    public String redirectKakaoLogin(@RequestParam(value = "code") String authCode, RedirectAttributes redirectAttributes) {
        kakaoService.requestAccessToken(authCode);
        if (kakaoService.checkRegistrationByEmail(email)) {
            String jwt = kakaoService.generateJWT(userService.kakaoLogin());
            log.info("JWT = {}", jwt);

            redirectAttributes.addAttribute("LoginKakao", true);
            redirectAttributes.addAttribute("needSignup", false);
            redirectAttributes.addAttribute("jwt", jwt);

            return "redirect:http://localhost:3000/";
        }

        return UriComponentsBuilder.fromUriString("redirect:http://localhost:3000/login")
                .queryParam("isKakao", true)
                .queryParam("needSignup", true)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();
    }
}
