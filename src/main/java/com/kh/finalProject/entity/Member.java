package com.kh.finalProject.entity;

import com.kh.finalProject.constant.Authority;
import com.kh.finalProject.constant.Existence;
import com.kh.finalProject.constant.Gender;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity // 디비로 생성
@Table(name = "t_member") // 테이블 이름 정의
@Getter @Setter @ToString
@NoArgsConstructor // 디폴트 생성자 생성
public class Member { // 사업자 회원, 아직 수정 중
    @Id
    @Column(name = "member_num_pk")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memberNum;
    @Column(unique = true)
    private String memberId;
    private String password;
    private String name;
    private String phone;
    @Column(unique = true)
    private String email;
    private LocalDate birthday; // 생년월일
    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별

    private LocalDate signUpDay; // 가입 날짜
    private Long followingId; // 내가 팔로우한 아이디 - 맵핑 아직 안함
    private Long followedId; // 나를 팔로우한 아이디 - 맵핑 아직 안함
    @Column(length = 2000)
    private String profileImgUrl; // 프로필 이미지 url
    @Column(length = 2000)
    private String intro; // 한 줄 소개

    @Enumerated(EnumType.STRING)
    private Existence existence; // 탈퇴 여부
    @Enumerated(EnumType.STRING)
    private Authority authority; // 회원 종류
    @Column(columnDefinition = "int default 0")
    private int totalPoint;

    @Builder
    public Member(String memberId, String password, String name, String phone, String email, LocalDate birthday,
                  Gender gender, LocalDate signUpDay, Existence existence, Authority authority) {
        this.memberId = memberId;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.signUpDay = signUpDay;
        this.existence = existence;
        this.authority = authority;
    }
    
    // 아래는 맵핑 수정 필요
    @OneToMany(mappedBy = "member")
    private List<Guild> guildList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<GuildMember> guildMemberList = new ArrayList<>();
    
    @OneToMany(mappedBy = "member")
    private List<Point> pointList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MyChallenge> myChallenges = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<CafeLike> cafeLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ReviewLike> reviewLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MyCoupon> myCoupons = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private Roulette roulette;
}
