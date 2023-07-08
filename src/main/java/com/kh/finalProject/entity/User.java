package com.kh.finalProject.entity;

import com.kh.finalProject.constant.Authority;
import com.kh.finalProject.constant.Existence;
import com.kh.finalProject.constant.Gender;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_User")
@Getter @Setter @ToString
@NoArgsConstructor
public class User { // 일반 회원
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userNum;
    @Column(unique = true)
    private String userId;
    private String name;
    private String password;
    private String phone;
    private String email;
    private LocalDateTime birthday; // 생년월일
    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별

    private LocalDateTime signUpTime; // 가입 시간
    private Long followingId; // 내가 팔로우한 아이디
    private Long followedId; // 나를 팔로우한 아이디
    private String profileImgUrl; // 프로필 이미지 url
    private String intro; // 한 줄 소개
    @Enumerated(EnumType.STRING)
    private Existence existence; // 탈퇴 여부
    @Enumerated(EnumType.STRING)
    private Authority authority; // 회원 종류

    @Builder
    public User(String userId, String password, String name, String email, String phone, LocalDateTime birthday, Gender gender, Existence existence, Authority authority) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthday = birthday;
        this.existence = existence;
        this.gender = gender;
        this.authority = authority;
    }


    @OneToMany(mappedBy = "user")
    private List<Guild> guildList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<GuildMember> guildMemberList = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private Point point;

}
