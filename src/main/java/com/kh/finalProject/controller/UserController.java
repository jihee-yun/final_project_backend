package com.kh.finalProject.controller;


import com.kh.finalProject.constant.Authority;
import com.kh.finalProject.constant.Gender;
import com.kh.finalProject.dto.UserDto;
import com.kh.finalProject.dto.UserResponseDto;
import com.kh.finalProject.entity.User;
import com.kh.finalProject.repository.UserRepository;
import com.kh.finalProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Boolean> userLogin(@RequestBody Map<String, String> loginData) {
        String userId = loginData.get("userId");
        String password = loginData.get("password");
        System.out.println("아이디 확인 : " + userId);
        System.out.print("비밀번호 : " + password);
        boolean result = userService.loginCheck(userId, password);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserDto signData) {
        String userId = signData.getUserId();
        String password = signData.getPassword();
        String name = signData.getName();
        String phone = signData.getPhone();
        String email = signData.getEmail();
        LocalDateTime birthDay = signData.getBirthday();
        Gender gender = signData.getGender();
        Authority authority = signData.getAuthority();

        boolean result = userService.regMember(userId, password, name, phone, email, birthDay, gender, authority);
        log.warn(String.valueOf(result));

        UserResponseDto response = UserResponseDto.builder()
                .userId(userId)
                .password(password)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Autowired
    private UserRepository userRepository;

    // 아이디 찾기
    @GetMapping("/findId")
    public ResponseEntity<List<UserDto>> findId(@RequestBody Map<String, String> find) {
        String email = find.get("email");
        List<UserDto> foundMembers = new ArrayList<>();
        try {
            // 이메일을 기반으로 아이디 찾기
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                UserDto userDto = new UserDto();
                userDto.setUserId(user.getUserId());
                foundMembers.add(userDto);
            }

            if (!foundMembers.isEmpty()) {
                return ResponseEntity.ok(foundMembers);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 비밀번호 찾기
    @GetMapping("/findPw")
    public ResponseEntity<Boolean> findPw(@RequestBody Map<String, String> find) {
        String email = find.get("email");

        String resultString = userService.findPw(email);
        boolean result = Boolean.parseBoolean(resultString);

        if (result == true) return new ResponseEntity<>(true, HttpStatus.OK);
        else return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
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

