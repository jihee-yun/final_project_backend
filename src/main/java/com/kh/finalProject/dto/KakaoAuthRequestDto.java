package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoAuthRequestDto {
    private String client_id;
    private String redirect_uri;
    private String code;
    private String secretKey;
    private String grant_Type;
}
