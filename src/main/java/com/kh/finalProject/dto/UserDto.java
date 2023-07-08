package com.kh.finalProject.dto;

import com.kh.finalProject.constant.Authority;
import com.kh.finalProject.constant.Existence;
import com.kh.finalProject.constant.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class UserDto {
    private Long userNum;
    private String userId;
    private String name;
    private String password;
    private String phone;
    private String email;
    private LocalDateTime birthday; // 생년월일
    private Gender gender; // 성별
    private LocalDateTime signUpTime; // 가입 시간
    private Long followingId; // 내가 팔로우한 아이디
    private Long followedId; // 나를 팔로우한 아이디
    private String profileImgUrl; // 프로필 이미지 url
    private String intro; // 한 줄 소개
    private Existence existence; // 탈퇴 여부
    private Authority authority; // 권한
}
