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
public class UserRefreshToken {
    @Id
    @GeneratedValue
    @Column(name = "token_num")
    private Long num;

    @Column(nullable = false)
    private String userId; // user 테이블 userId

    @Column(nullable = false)
    private String userRefreshToken; // 리프레시 토큰

    public UserRefreshToken(String userId, String userRefreshToken) {
        this.userId = userId;
        this.userRefreshToken = userRefreshToken;
    }

    public UserRefreshToken setRefreshToken(String newRefreshToken) {
        this.userRefreshToken = newRefreshToken;
        return this;
    }
}
