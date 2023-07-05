package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AccessTokenDto {
    private String tokenType;
    private String accessToken;
    private String accessTokenExpiresIn;
    private String refreshToken;
    private String refreshTokenExpiresIn;

    public AccessTokenDto(String accessToken) {
        accessToken = this.accessToken;
    }
}
