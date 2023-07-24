package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Guild;
import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuildRepository extends JpaRepository<Guild, Long> {
    Optional<Guild> findById(Long id);

    // 생성일 기준으로 최신 길드 목록 가져오기
    List<Guild> findAllByOrderByCreateDateDesc();

    // 카테고리로 길드 목록 가져오기
    List<Guild> findAllByCategoryOrderByCreateDateDesc(int category);

    // 가입 여부 확인
//    @Query("SELECT g.user.userNum FROM Guild g WHERE g.id = :guildId AND g.user.userNum = :memberId")
//    Optional<Long> findUserNumByGuildIdAndMemberId(@Param("guildId") Long guildId, @Param("memberId") Long memberId);

    @Query("SELECT g.member.memberNum FROM Guild g WHERE g.id = :guildId AND g.member.memberNum = :memberId")
    Optional<Long> findUserNumByGuildIdAndMemberId(@Param("guildId") Long guildId, @Param("memberId") Long memberId);

    // 회원 객체로 길드 정보 전체 조회
    List<Guild> findByMember(Member member);

}
