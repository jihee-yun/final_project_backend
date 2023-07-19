package com.kh.finalProject.dto;

import com.kh.finalProject.constant.Authority;
import com.kh.finalProject.constant.Existence;
import com.kh.finalProject.constant.Gender;
import com.kh.finalProject.entity.Member;
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
public class MemberRequestDto {
    private String memberId;
    private String password;
    private String name;
    private String phone;
    private String email;
    private LocalDate birthday;
    private Gender gender;
    private Authority authority;
    private int points;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .memberId(memberId)
                .password(passwordEncoder.encode(password)) // 암호화된 값으로 DB에 저장됨
                .name(name)
                .phone(phone)
                .email(email)
                .birthday(birthday)
                .gender(gender)
                .signUpDay(LocalDate.now()) // 오늘 날짜 입력(2023-01-01 같은 형식)
                .existence(Existence.Yes) // 자동으로 존재 회원
                .authority(authority) // 회원 종류
                .build();
    }
    // 로그인시 넘겨받은 아이디와 비밀번호 조합으로 토큰 생성 준비
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(memberId, password);
    }
}
