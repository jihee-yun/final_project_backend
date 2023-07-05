package com.kh.finalProject.service;

import com.kh.finalProject.entity.User;
import com.kh.finalProject.jwt.TokenProvider;
import com.kh.finalProject.repository.RefreshTokenRepository;
import com.kh.finalProject.repository.UserRepository;
import com.kh.finalProject.security.AccessTokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AccessTokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validateToken(refreshToken)) {
            throw new AccessTokenExpiredException("Refresh Token 만료되었습니다.");
        }

        Long userNum = refreshTokenRepository.findByRefreshToken(refreshToken).get().getUserNum();
        User user = userRepository.findByUserNum(userNum).get();
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getAuthority().toString()));
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(String.valueOf(user.getUserNum()))
                .password(user.getPassword())
                .authorities(authorities)
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", authorities);

        return tokenProvider.generateAccessTokenDto(authentication).getAccessToken();
    }
}
