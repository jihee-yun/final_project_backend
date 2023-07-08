package com.kh.finalProject.service;


import com.kh.finalProject.constant.Authority;
import com.kh.finalProject.constant.Gender;
import com.kh.finalProject.dto.*;
import com.kh.finalProject.entity.User;
import com.kh.finalProject.jwt.TokenProvider;
import com.kh.finalProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final HttpSession session;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    // 회원 가입
    public boolean regMember(String userId, String password, String name, String phone, String email,
                             LocalDate birthDay, Gender gender, Authority authority) {
        if (userRepository.findByUserId(userId).isEmpty()) {
            User user = new User();
            user.setUserId(userId);
            user.setPassword(password);
            user.setName(name);
            user.setPhone(phone);
            user.setEmail(email);
            user.setBirthday(birthDay);
            user.setGender(gender);
            user.setAuthority(authority);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // 일반 회원 가입
    public UserResponseDto signup(UserRequestDto userRequestDto) {
        if (userRepository.existsByUserId(userRequestDto.getUserId())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        User user = userRequestDto.toUser(passwordEncoder);
        return UserResponseDto.of(userRepository.save(user));
    }

//    public boolean regMemberCheck(User user) {
//        Optional<User> findMember = userRepository.findByEmail(user.getEmail());
//        return findMember.isEmpty();
//    }

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
            userDto.setPassword(info.getPassword());
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

    // 비밀번호 찾기
    public String findPw(String email) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                return user.getPassword(); // 비밀번호 반환
            } else {
                return null; // 멤버가 존재하지 않는 경우 null 반환
            }
        } catch (Exception e) {
            // 예외 처리
            return null;
        }
    }

    // 회원가입 여부
    public boolean checkUserIdExist(String userId) {
        Optional<User> userInfo = userRepository.findByUserId(userId);
        return userInfo.isPresent();
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
}
