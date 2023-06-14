package com.kh.finalProject.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    private String grantType;       // 토큰의 유형
    private String accessToken;     // 실제 사용될 토큰
    private Long tokenExpiresIn;    //
}
