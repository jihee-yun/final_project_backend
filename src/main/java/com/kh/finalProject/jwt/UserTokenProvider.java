package com.kh.finalProject.jwt;

import com.kh.finalProject.dto.UserTokenDto;
import com.kh.finalProject.entity.UserRefreshToken;
import com.kh.finalProject.repository.UserRefreshTokenRepository;
import com.kh.finalProject.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserTokenProvider {
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final UserService userService;

    // 토큰을 생성하고 검증할 때 사용하는 문자열
    private static final String AUTHORITIES_KEY = "sweetkingdom!";
    private static final String BEARER_TYPE = "Bearer";
    // 토큰의 만료 시간
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 60 * 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1L * 24 * 60 * 60 * 1000;
    // 암호화 키 생성을 위해 사용
    private Key key;

    // 주의점: 여기서 @Value는 `springframework.beans.factory.annotation.Value`소속이다! lombok의 @Value와 착각하지 말것!
    public UserTokenProvider(@Value("${springboot.jwt.secret}") String secretKey, UserRefreshTokenRepository userRefreshTokenRepository, @Lazy UserService userService){
        this.userRefreshTokenRepository = userRefreshTokenRepository;
        this.userService = userService;
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    // 최초 엑세스, 리프레시 토큰 동시 생성
    public UserTokenDto generateUserToken(Authentication authentication) {
        String userId = authentication.getName();
        Long userNum = userService.getUserNumByUserId(userId);
        String userName = userService.getUserNameByUserId(userId);


        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        System.out.println(accessTokenExpiresIn);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim("userNum", userNum)
                .claim("userName", userName)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        String userRefreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        UserRefreshToken userTk = userRefreshTokenRepository.findByUserId(authentication.getName())
                .map(tokenEntity -> tokenEntity.setRefreshToken(userRefreshToken))
                .orElse(new UserRefreshToken(authentication.getName(), userRefreshToken));
        userRefreshTokenRepository.save(userTk);

        return UserTokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(userRefreshToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshTokenExpiresIn(refreshTokenExpiresIn.getTime())
                .build();
        }

    // 자격 증명 메소드
    public Authentication getUserAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

        // 리프레쉬 토큰을 확인 후 AccessToken 재발급 메소드
        public UserTokenDto regenerateAccessUserToken(Authentication authentication) {
            String authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            long nowTime = (new Date()).getTime();
            Date accessTokenExpiresIn = new Date(nowTime + ACCESS_TOKEN_EXPIRE_TIME);

            String accessToken = Jwts.builder()
                    .setSubject(authentication.getName())
                    .claim(AUTHORITIES_KEY, authorities)
                    .setExpiration(accessTokenExpiresIn)
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();

            return UserTokenDto.builder()
                    .grantType(BEARER_TYPE)
                    .accessToken(accessToken)
                    .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                    .authority(authorities)
                    .build();
        }


        // 토큰이 유효한지 검증하는 메소드
        public boolean validateUserToken(String token) {
            try {
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
                return true;
            } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
                log.info("잘못된 JWT 서명입니다.");
            } catch (ExpiredJwtException e) {
                log.info("만료된 JWT 토큰입니다.");
            } catch (UnsupportedJwtException e) {
                log.info("지원되지 않는 JWT 토큰입니다.");
            } catch (IllegalArgumentException e) {
                log.info("JWT 토큰이 잘못되었습니다.");
            }
            return false;
        }

        // 토큰 파싱 메소드
        private Claims parseClaims(String accessToken) {
            try {
                return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
            } catch (ExpiredJwtException e) {
                return e.getClaims();
            }
        }
    }

