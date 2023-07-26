package com.kh.finalProject.controller;

import com.kh.finalProject.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mail")
@CrossOrigin(origins = "http://localhost:3000")
public class MailController {

    private final MailService mailService;

    @PostMapping("/confirm")
    public ResponseEntity<Boolean> mailConfirm(@RequestBody Map<String, String> mailData) {
        String email = mailData.get("email");
        boolean result = mailService.confirm(email);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyCode(@RequestBody Map<String, String> verifyData) {
        String email = verifyData.get("email");
        String code = verifyData.get("code");
        boolean result = mailService.verifyCode(email, code);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
