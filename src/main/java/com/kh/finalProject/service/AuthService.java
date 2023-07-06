package com.kh.finalProject.service;

import com.kh.finalProject.dto.*;
import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.User;
import com.kh.finalProject.jwt.TokenProvider;
import com.kh.finalProject.repository.MemberRepository;
import com.kh.finalProject.repository.RefreshTokenRepository;
import com.kh.finalProject.repository.UserRepository;
import com.kh.finalProject.security.AccessTokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


//    // 사용자 회원가입
//    public UserResponseDto userSignUp(UserRequestDto userRequestDto) {
//        Optional<User> existingUser = userRepository.findByUserId(userRequestDto.getUserId());
//        if(existingUser.isPresent()) {
//            throw new RuntimeException("이미 가입된 유저입니다.");
//        }
//
//        User user = userRequestDto.toUser(passwordEncoder);
//        return UserResponseDto.of(userRepository.save(user));
//    }
//
//    // 사용자 로그인
//    public TokenDto userLogin(UserRequestDto userRequestDto) {
//        UsernamePasswordAuthenticationToken authenticationToken = userRequestDto.toAuthentication();
//        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
//        return tokenProvider.generateTokenDto(authentication);
//    }


    // 엑세스 토큰 재발급
    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validateToken(refreshToken)) {
            throw new AccessTokenExpiredException("Refresh Token 만료");
        }

        String memberId = refreshTokenRepository.findByRefreshToken(refreshToken).get().getMemberId();
        Member member = memberRepository.findByMemberId(memberId).get();
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(member.getAuthority().toString()));
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(String.valueOf(member.getMemberId()))
                .password(member.getPassword())
                .authorities(authorities)
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", authorities);

        return tokenProvider.regenerateAccessToken(authentication).getAccessToken();
    }
}
