package com.kh.finalProject.security;

import com.kh.finalProject.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component

public class WebSecurityConfig {
        private final TokenProvider tokenProvider;
        private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
        private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

        @Bean
        public WebClient webClient() {
            return WebClient.create();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .httpBasic().disable() // HTTP 기본 인증을 하지 않도록 설정
                    .csrf().disable() // 위변조 관련 보호 정책을 비활성화
                    //세션을 사용하지 않고, 상태를 유지하지 않는 세션 관리 정책을 설정
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler)

                    .and()
                    .authorizeRequests()
                    // 경로에 대해 인증 없이 접근을 허용
                    .antMatchers("/auth/**", "/mypage/**", "/cafe/**", "/event/**", "/koauth/**", "/couponstore/**", "/guild/**", "/user/**", "/roulette/**", "/quizmain/**", "/member/**").permitAll() // 어떤 패스로 들어올 때 접근을 허용해줄건지
                    .anyRequest().authenticated()

                    .and()
                    .apply(new JwtSecurityConfig(tokenProvider));

            return http.build();
        }
}
