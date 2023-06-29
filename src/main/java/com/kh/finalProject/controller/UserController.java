package com.kh.finalProject.controller;


import com.kh.finalProject.dto.UserDto;
import com.kh.finalProject.entity.User;
import com.kh.finalProject.repository.UserRepository;
import com.kh.finalProject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 로그인
    @PostMapping("/login")
    public String userLogin(@RequestBody Map<String, String> loginData) {
        String userId = loginData.get("userId");
        String pw = loginData.get("pw");
        System.out.println("아이디 패스워드 확인 : "+userId + " " + pw);
        boolean result = userService.loginCheck(userId, pw);
        return Boolean.toString(result);
    }

    // 회원가입
    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody Map<String, String> signData) {
        try {
            String userId = signData.get("userId");
            String name = signData.get("name");
            String pw = signData.get("pw");
            boolean result = userService.regMember(userId, name, pw);
            log.warn(String.valueOf(result));
            if (result) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                log.warn("값이 false");
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.warn("Controller 오류");
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
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
        boolean result =  Boolean.parseBoolean(resultString);

        if(result == true) return new ResponseEntity<>(true, HttpStatus.OK);
        else return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    // 회원가입 중복
    @GetMapping("/check")
    public ResponseEntity<Boolean> doubleCheck(@RequestParam("userId") String userId) {
        try {
            boolean result = userService.checkUserIdExist(userId);
            if(result) {
                return new ResponseEntity<>(true, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}

