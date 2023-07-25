package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordDto {
    private String userNum;
    private String name;
    private String userId;
    private String password;
    private String email;
    private String phone;
    private String newPassword;
    private String authority;
}
