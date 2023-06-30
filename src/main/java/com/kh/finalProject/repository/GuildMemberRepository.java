package com.kh.finalProject.repository;

import com.kh.finalProject.entity.GuildMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuildMemberRepository extends JpaRepository<GuildMember, Long> {

    // 해당 길드 길드원 조회
    List<GuildMember> findByGuildId(Long guildId);
}
