package com.kh.finalProject.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue
    @Column(name = "token_num")
    private Long num;             // 토큰 번호

    @Column(nullable = false)
    private Long userNum;         // UserTb Primary Key

    @Column(nullable = false)
    private String refreshToken; // 리프레시 토큰

    public RefreshToken(Long userNum, String refreshToken) {
        this.userNum = userNum;
        this.refreshToken = refreshToken;
    }

    public RefreshToken update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
        return this;
    }
}
