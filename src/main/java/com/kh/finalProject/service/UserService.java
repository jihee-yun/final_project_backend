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
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

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
//    public String findId(String name, String email) {
//        Optional<User> userInfo = userRepository.findByNameAndEmail(name, email);
//        System.out.println("정보 : " + name + " " + email + " " + userInfo);
//        if (!userInfo.isPresent()) {
//            System.out.println("아이디를 찾지 못함");
//            return null; // 아이디를 찾지 못한 경우 null을 반환하거나 원하는 대응을 수행
//        }
//        User user = userInfo.get();
//        UserDto userDto = new UserDto();
//        userDto.setUserId(user.getUserId());
//        System.out.println("ID 찾기 :" + userDto.getUserId());
//        String result = user.getUserId();
//        return result;
//    }

    // 비밀번호 찾기
//    public Boolean findPw(String name, String email, String phone) {
//        Optional<User> userOptional = userRepository.findByNameAndPhoneAndEmail(name, phone, email);
//        AtomicBoolean result = new AtomicBoolean(false);
//        userOptional.ifPresent(user -> {
//            String temporaryPassword = generateTemporaryPassword();
//            user.setPassword(passwordEncoder.encode(temporaryPassword)); // 비밀번호 암호화하여 설정
//            userRepository.save(user);
//            sendTemporaryPasswordEmail(user.getEmail(), temporaryPassword);
//            result.set(true);
//        });
//        return result.get();
//    }

//    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
//    private static final int PASSWORD_LENGTH = 10;
//
//    private String generateTemporaryPassword() {
//        StringBuilder temporaryPassword = new StringBuilder();
//        SecureRandom secureRandom = new SecureRandom();
//
//        for (int i = 0; i < PASSWORD_LENGTH; i++) {
//            int randomIndex = secureRandom.nextInt(CHARACTERS.length());
//            temporaryPassword.append(CHARACTERS.charAt(randomIndex));
//        }
//
//        return temporaryPassword.toString();
//    }
//
//    private void sendTemporaryPasswordEmail(String email, String temporaryPassword) {
//        // 여기서는 간단하게 콘솔에 메시지를 출력하는 것으로 대체합니다.
//        System.out.println("임시 비밀번호를 다음 이메일로 발송합니다 : " + email);
//        System.out.println("임시 비밀번호 : " + temporaryPassword);
//    }
//    public String findPw(String userId, String email) {
//        Optional<User> userInfo = userRepository.findByUserIdAndEmail(userId, email);
//        System.out.println("정보 : " +" " + userId + " " + email + " " + userInfo);
//        if(!userInfo.isPresent()) {
//            System.out.println("비밀번호를 찾지 못함");
//            return null;
//        }
//        User user = userInfo.get();
//        UserDto userDto = new UserDto();
//        userDto.setPassword(user.getPassword());
//        System.out.println("비밀번호 찾기 : " + userDto.getPassword());
//        String result = user.getPassword();
//        return result;
//    }


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
            userDto.setUserNum(info.getUserNum());
            userDto.setUserId(info.getUserId());
            userDto.setName(info.getName());
            userDto.setPhone(info.getPhone());
            userDto.setEmail(info.getEmail());
            userDto.setBirthday(info.getBirthday());
            userDto.setSignUpTime(info.getSignUpTime());
            userDto.setGender(info.getGender());
            userDto.setExistence(info.getExistence());
            userDto.setAuthority(info.getAuthority());
            userDtos.add(userDto);
        }
        return userDtos;
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
