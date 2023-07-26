package com.kh.finalProject.repository;

import com.kh.finalProject.entity.GuildMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuildMemberRepository extends JpaRepository<GuildMember, Long> {

    // 해당 길드 길드원 조회
    List<GuildMember> findByGuildId(Long guildId);

    // 회원 번호를 기준으로 조회
    @Query("SELECT gm.guild.guildName FROM GuildMember gm WHERE gm.member.memberNum = :memberNum")
    List<String> findGuildNamesByMemberNum(@Param("memberNum") Long memberNum);

    // 가입 여부 확인
//    @Query("SELECT gm.user.userNum FROM GuildMember gm WHERE gm.guild.id = :guildId AND gm.user.userNum = :memberId")
//    Optional<Long> findMemberNumByGuildIdAndMemberId(@Param("guildId") Long guildId, @Param("memberId") Long memberId);

    @Query("SELECT gm.member.memberNum FROM GuildMember gm WHERE gm.guild.id = :guildId AND gm.member.memberNum = :memberId")
    Optional<Long> findMemberNumByGuildIdAndMemberId(@Param("guildId") Long guildId, @Param("memberId") Long memberId);
}
