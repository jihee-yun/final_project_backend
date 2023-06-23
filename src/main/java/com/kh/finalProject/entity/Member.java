package com.kh.finalProject.entity;

import com.kh.finalProject.constant.Authority;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // 디비로 생성
@Table(name = "member") // 테이블 이름 정의
@Getter @Setter @ToString
@NoArgsConstructor // 디폴트 생성자 생성
public class Member { // 사업자 회원, 아직 강사님 코드 그대로
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;
    private String name;
    private String password;
    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public Member(String user, String email, String password, String name, Authority authority) {
        this.userId = user;
        this.email = email;
        this.password = password;
        this.name = name;
        this.authority = authority;
    }
}
