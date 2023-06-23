package com.kh.finalProject.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${springboot.jw.secret}")
    private String jwtSecret;
    @Value("${springboot.secret.Expiration}")
    private int accessTokenExpiration;
    @Value("${springboot.jwt.secret.Expiration}")
    private int refreshTokenExpiration;

    private final SecretKey secretKey;

    public JwtProvider() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public String generateAccessToken(String userId) {
        Date date = new Date();
        Date expDate = new Date(date.getTime() + accessTokenExpiration*1000);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(date)
                .setExpiration(expDate)
                .signWith(secretKey)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }
}
