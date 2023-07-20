package com.kh.finalProject.dto;

import com.kh.finalProject.constant.Authority;
import com.kh.finalProject.constant.Gender;
import com.kh.finalProject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private String userId;
    private String password;
    private String name;
    private String phone;
    private String email;
    private LocalDate birthDay;
    private LocalDate signUpTime;
    private Gender gender;
    private Authority authority;
    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .birthDay(user.getBirthday())
                .signUpTime(user.getSignUpTime())
                .gender(user.getGender())
                .authority(user.getAuthority())
                .build();

    }
}
