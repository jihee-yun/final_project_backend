package com.kh.finalProject.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
// OncePerRequestFilter 상속받아 JWT 토큰을 필터링하는 역할을 수행하는 JwtFilter 클래스
public class UserJwtFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private UserTokenProvider userTokenProvider;

    private String resolveUserToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER); // 해더에서 Authorization 해더 추출
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) { // grantType이 "Bearer"이면
            return bearerToken.substring(7);
        }
        return null;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveUserToken(request); // grantType 제거

        if (StringUtils.hasText(jwt) && userTokenProvider.validateUserToken(jwt)) {
            Authentication authentication = userTokenProvider.getUserAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
