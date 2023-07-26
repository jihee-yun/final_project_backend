package com.kh.finalProject.dto;

import com.kh.finalProject.constant.Authority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDto {
    private Long memberNum;
    private String memberId;
    private String password;
    private String email;
    private String newPassword;
    private Authority authority; // 회원 종류 구분


}
