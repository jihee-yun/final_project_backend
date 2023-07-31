package com.kh.finalProject.entity;

import com.kh.finalProject.constant.Authority;
import com.kh.finalProject.constant.Existence;
import com.kh.finalProject.constant.Gender;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_User")
@Getter @Setter
@NoArgsConstructor
public class User { // 일반 회원
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userNum;
    @Column(name = "user_id", unique = true)
    private String userId;
    private String name;
    private String password;
    private String phone;
    private String email;
    private LocalDate birthday; // 생년월일
    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별

    private LocalDate signUpTime; // 가입 날짜
    private Long followingId; // 내가 팔로우한 아이디
    private Long followedId; // 나를 팔로우한 아이디
    @Column(length = 2000)
    private String profileImgUrl; // 프로필 이미지 url
    @Column(length = 2000)
    private String intro; // 한 줄 소개
    @Enumerated(EnumType.STRING)
    private Existence existence; // 탈퇴 여부
    @Enumerated(EnumType.STRING)
    private Authority authority; // 회원 종류

    @Builder
    public User(String userId, String password, String name, String email, String phone, LocalDate birthday, LocalDate signUpTime, Gender gender, Existence existence, Authority authority) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthday = birthday;
        this.signUpTime = signUpTime;
        this.existence = existence;
        this.gender = gender;
        this.authority = authority;
    }
}
