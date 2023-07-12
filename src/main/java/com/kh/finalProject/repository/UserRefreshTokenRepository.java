package com.kh.finalProject.repository;

import com.kh.finalProject.entity.RefreshToken;
import com.kh.finalProject.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    Optional<UserRefreshToken> findByUserId(String userId);
    Optional<UserRefreshToken> findByUserRefreshToken(String userRefreshToken);
}
