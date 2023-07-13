package com.kh.finalProject.service;

import com.kh.finalProject.dto.*;
import com.kh.finalProject.entity.User;
import com.kh.finalProject.jwt.UserTokenProvider;
import com.kh.finalProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final UserRepository userRepository;
    private final HttpSession session;
    private final PasswordEncoder passwordEncoder;
    private final UserTokenProvider tokenProvider;

    // 일반 회원 로그인
    public UserTokenDto userLogin(UserRequestDto userRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = userRequestDto.toAuthentication();
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateUserToken(authentication);
    }

    // 일반 회원 가입
    public UserResponseDto signUp(UserRequestDto userRequestDto) {
        if (userRepository.existsByUserId(userRequestDto.getUserId())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        User user = userRequestDto.toUser(passwordEncoder);
        return UserResponseDto.of(userRepository.save(user));
    }

    // 아이디 찾기
    public String findUserIdByEmail(String email) {
        Optional<User> userInfo = userRepository.findByEmail(email);
        if (userInfo.isPresent()) {
            User user = userInfo.get();
            return user.getUserId();
        } else {
            throw new RuntimeException("일치하는 회원 정보를 찾을 수 없습니다.");
        }
    }

    // 비밀번호 찾기
    public String findPassword(String email) {
        Optional<User> userInfo = userRepository.findByEmail(email);
        if (userInfo.isPresent()) {
            String temporaryPassword = generateTemporaryPassword();
            User user = userInfo.get();
            user.setPassword(passwordEncoder.encode(temporaryPassword));
            userRepository.save(user);

            return "임시 비밀번호를 이메일로 전송하였습니다.";
        } else {
            throw new RuntimeException("일치하는 회원 정보를 찾을 수 없습니다.");
        }
    }

    // 임시 비밀번호 생성 메서드
    private String generateTemporaryPassword() {
        int length = 10; // 임시 비밀번호 길이 설정
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()"; // 사용할 문자 범위 설정

        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }


    // 회원가입 여부
    public boolean checkUserIdExist(String userId) {
        Optional<User> userInfo = userRepository.findByUserId(userId);
        return userInfo.isPresent();
    }

    // 로그인 체크
    public boolean loginCheck(String userId, String password) {
        Optional<User> memberInfo = userRepository.findByUserIdAndPassword(userId, password);
        return memberInfo.isPresent();
    }

    // 회원 조회
    public List<UserDto> getMemberList() {
        List<User> userInfoList;
        userInfoList = userRepository.findAll();

        List<UserDto> userDtos = new ArrayList<>();
        for (User info : userInfoList) {
            UserDto userDto = new UserDto();
            userDto.setUserId(info.getUserId());
            userDto.setName(info.getName());
            userDto.setPhone(info.getPhone());
            userDto.setEmail(info.getEmail());
            userDto.setBirthday(info.getBirthday());
            userDto.setGender(info.getGender());
            userDto.setAuthority(info.getAuthority());
            userDtos.add(userDto);
        }
        return userDtos;
    }

    // 카카오
    public UserDto kakaoLogin() {
        String email = (String) session.getAttribute("email");
        log.info("userService.kakaoLogin mail check = {}", email);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, "");
        log.info("userService.kakaoLogin authentication token check = {}", authenticationToken);

//        Authentication authentication = kakaoAuthProvider.authenticate(authenticationToken);
//        log.info("authentication = {}", authentication);

//        return tokenProvider.generateTokenDto(authentication);
        return null;
    }

    // 유저 아이디로 유저 번호 조회
    public Long getUserNumByUserId(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        if(user.isPresent()) {
            User user1 = user.get();
            return user1.getUserNum();
        }
        return null;
    }

    // 유저 아이디로 유저 이름 조회
    public String getUserNameByUserId(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        if(user.isPresent()) {
            User user1 = user.get();
            return user1.getName();
        }
        return null;
    }
}
