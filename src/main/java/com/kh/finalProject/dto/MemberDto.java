package com.kh.finalProject.dto;

import com.kh.finalProject.constant.Authority;
import com.kh.finalProject.constant.Existence;
import com.kh.finalProject.constant.Gender;
import com.kh.finalProject.entity.Guild;
import com.kh.finalProject.entity.GuildMember;
import com.kh.finalProject.entity.Point;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MemberDto {
    private Long memberNum;
    private String memberId;
    private String password;
    private String name;
    private String phone;
    private String email;
    private Date birthday; // 생년월일
    private Gender gender; // 성별
    private LocalDate signUpDay; // 가입 시간
    private Long followingId; // 내가 팔로우한 아이디
    private Long followedId; // 나를 팔로우한 아이디
    private String profileImgUrl; // 프로필 이미지 url
    private String intro; // 한 줄 소개
    private Existence existence; // 탈퇴 여부
    private Authority authority; // 회원 종류 구분

    // 길드 테이블 관련
    private List<Guild> guildList = new ArrayList<>();
    private List<GuildMember> guildMemberList = new ArrayList<>();

    // 포인트 테이블 관련
    private Point point;




}
