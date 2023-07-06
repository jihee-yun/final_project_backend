package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class RefreshTokenDto {
    @Id
    @GeneratedValue
    @Column(name = "token_num")
    private Long num; // 토큰 구분 번호

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String refreshToken;

    public RefreshTokenDto(String memberId, String refreshToken) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
    }

    public RefreshTokenDto update(String newRefreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }
}
