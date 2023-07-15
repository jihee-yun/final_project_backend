package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Guild;
import com.kh.finalProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GuildRepository extends JpaRepository<Guild, Long> {
    Optional<Guild> findById(Long id);

    // 가입 여부 확인
    @Query("SELECT g.user.userNum FROM Guild g WHERE g.id = :guildId AND g.user.userNum = :memberId")
    Optional<Long> findUserNumByGuildIdAndMemberId(@Param("guildId") Long guildId, @Param("memberId") Long memberId);
}
