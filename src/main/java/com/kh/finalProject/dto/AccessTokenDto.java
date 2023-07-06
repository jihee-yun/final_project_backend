package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessTokenDto { // 수정 예정
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private String accessTokenExpiresIn;
    private String refreshTokenExpiresIn;

    public AccessTokenDto(String accessToken) {
        accessToken = this.accessToken;
    }
}
