package com.kh.finalProject.controller;


import com.kh.finalProject.dto.*;
import com.kh.finalProject.repository.UserRepository;
import com.kh.finalProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    private UserRepository userRepository;

    // 회원 조회
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getMemberList() {
        List<UserDto> users = userService.getMemberList();
        return ResponseEntity.ok(users);
    }

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

    // 아이디 찾기
//    @PostMapping("/findId")
//    public ResponseEntity<String> findId(@RequestBody Map<String, String> findIdData) {
//        String name = findIdData.get("name");
//        String email = findIdData.get("email");
//        String memberDto = memberService.findId(name, email);
//        if (memberDto == null) {
//            // 아이디를 찾지 못한 경우
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//        // 아이디를 찾은 경우
//        return ResponseEntity.ok(memberDto);
//    }



//    // 비밀번호 찾기
//    @PostMapping("/findPw")
//    public ResponseEntity<Boolean> findPw(@RequestBody UserPasswordDto userPasswordDto) {
//        String name = userPasswordDto.getName();
//        String phone = userPasswordDto.getPhone();
//        String email = userPasswordDto.getEmail();
//        Boolean result = userService.findPw(name, phone, email);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//    public ResponseEntity<String> findPw(@RequestBody Map<String, String> findPwData) {
//        String userId = findPwData.get("userId");
//        String email = findPwData.get("email");
//        String userDto = userService.findPw(userId, email);
//        if(userDto == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//        return ResponseEntity.ok(userDto);
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

