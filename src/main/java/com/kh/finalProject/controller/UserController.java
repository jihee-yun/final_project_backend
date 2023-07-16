package com.kh.finalProject.controller;


import com.kh.finalProject.dto.UserRequestDto;
import com.kh.finalProject.dto.UserResponseDto;
import com.kh.finalProject.dto.UserTokenDto;
import com.kh.finalProject.repository.UserRepository;
import com.kh.finalProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<UserTokenDto> userLogin(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.userLogin(userRequestDto));
    }

    // 회원가입
    @PostMapping("/new")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.signUp(userRequestDto));
    }

    @Autowired
    private UserRepository userRepository;

    // 아이디 찾기
    @GetMapping("/findId")
    public ResponseEntity<String> findUserId(@RequestParam("email") String email) {
        String userId = userService.findUserIdByEmail(email);
        return ResponseEntity.ok(userId);
    }

    @PostMapping("/findPw")
    public ResponseEntity<String> findPassword(@RequestBody Map<String, String> requestMap) {
        String email = requestMap.get("email");
        try {
            userService.findPassWordByEmail(email);
            return ResponseEntity.ok("임시 비밀번호가 이메일로 발송되었습니다.");
        } catch (RuntimeException e) {
            // 서버 내부 에러가 발생한 경우 클라이언트에게 에러 메시지를 전달하는 대신
            // 사용자 친화적인 메시지를 작성하여 클라이언트로 반환하거나 에러 코드를 정의하여 반환할 수 있습니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("임시 비밀번호 발송에 실패했습니다.");
        }
    }

    // 회원가입 중복
    @GetMapping("/check")
    public ResponseEntity<Boolean> doubleCheck(@RequestParam("userId") String userId) {
        boolean result = userService.checkUserIdExist(userId);
        if (result) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
}

