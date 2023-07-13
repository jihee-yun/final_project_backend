package com.kh.finalProject.security;


import com.kh.finalProject.jwt.JwtFilter;
import com.kh.finalProject.jwt.TokenProvider;
import com.kh.finalProject.jwt.UserJwtFilter;
import com.kh.finalProject.jwt.UserTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenProvider tokenProvider; // 의존성 주입
    private final UserTokenProvider userTokenProvider;

    @Override
    public void configure(HttpSecurity http) {
        JwtFilter customFilter = new JwtFilter(tokenProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
        UserJwtFilter customFilter2 = new UserJwtFilter(userTokenProvider);
        http.addFilterBefore(customFilter2, UsernamePasswordAuthenticationFilter.class);
    }

}
