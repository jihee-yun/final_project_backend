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

    // 비밀번호 찾기
//    @GetMapping("findPw")
//    public ResponseEntity<String> findPassword(@RequestParam("email") String email) {
//        String message = userService.findPassword(email);
//        return ResponseEntity.ok(message);
//    }

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

