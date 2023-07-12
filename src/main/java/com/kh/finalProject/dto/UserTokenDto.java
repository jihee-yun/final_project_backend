package com.kh.finalProject.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 엑세스와 리프레시 토큰 한번에 묶어둠
public class UserTokenDto {
    private String grantType;       // 토큰의 유형(Bearer 넣게됨)
    private String accessToken;     // 통신으로 주고 받을 엑세스 토큰
    private String refreshToken;
    private Long accessTokenExpiresIn;    // 만료 시간
    private Long refreshTokenExpiresIn;
    private String authority;
}
