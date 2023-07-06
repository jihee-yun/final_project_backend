package com.kh.finalProject.controller;


import com.kh.finalProject.dto.*;
import com.kh.finalProject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
//    private final AuthService authService;

//    @PostMapping("/signup")
//    public ResponseEntity<UserResponseDto> userSignUp(@RequestBody UserRequestDto userRequestDto) {
//        return ResponseEntity.ok(authService.userSignUp(userRequestDto));
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<TokenDto> userLogin(@RequestBody UserRequestDto userRequestDto) {
//        return ResponseEntity.ok(authService.userLogin(userRequestDto));
//    }

}
