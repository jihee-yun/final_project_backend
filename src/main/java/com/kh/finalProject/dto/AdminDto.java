package com.kh.finalProject.dto;

import com.kh.finalProject.constant.Authority;
import com.kh.finalProject.constant.Existence;
import com.kh.finalProject.constant.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdminDto {
    private String adminId;
    private String password;
    private String name;
    private String phone;
    private LocalDate birthday; // 생년월일
    private Gender gender; // 성별
    private Existence existence; // 탈퇴 여부
    private Authority authority; // 권한
}
