package com.kh.finalProject.repository;


import com.kh.finalProject.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
//    Optional<RefreshToken> findByUserNum(Long userNum);
//    Optional<RefreshToken> findByMemberNum(Long memberNum);
    Optional<RefreshToken> findByMemberId(String memberId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
