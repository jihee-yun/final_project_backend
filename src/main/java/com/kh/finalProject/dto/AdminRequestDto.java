package com.kh.finalProject.dto;

import com.kh.finalProject.constant.Authority;
import com.kh.finalProject.constant.Gender;
import com.kh.finalProject.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRequestDto {
    private String adminId;
    private String password;
    private String name;
    private String phone;
    private LocalDate birthday;
    private Gender gender;
    private Authority authority;

    public Admin toAdmin() {
        return Admin.builder()
                .adminId(adminId)
                .password(password)
                .name(name)
                .phone(phone)
                .birthday(birthday)
                .gender(gender)
                .authority(authority)
                .build();
    }
}
