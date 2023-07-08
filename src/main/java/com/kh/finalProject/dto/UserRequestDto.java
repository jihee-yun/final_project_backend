package com.kh.finalProject.dto;

import com.kh.finalProject.constant.Authority;
import com.kh.finalProject.constant.Existence;
import com.kh.finalProject.constant.Gender;
import com.kh.finalProject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
    private String userId;
    private String password;
    private String name;
    private String phone;
    private String email;
    private LocalDate birthday;
    private Gender gender;
    private Authority authority;
    private Existence existence;


    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .phone(phone)
                .email(email)
                .birthday(birthday)
                .gender(gender)
                .authority(Authority.ROLE_USER)
                .existence(Existence.Yes)
                .build();
    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(userId, password);
    }
}
