package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.MyChallenge;
import com.kh.finalProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MyChallengeRepository extends JpaRepository<MyChallenge, Long>{
    Optional<MyChallenge> findById(Long id);
    List<MyChallenge> findByMember(Member member);

    // 회원 번호를 이용해 챌린지 이름 한 번에 찾기
    @Query("SELECT mc.challenge.challengeName FROM MyChallenge mc WHERE mc.member.memberNum = :memberNum")
    List<String> findChallengeNamesByMemberNum(@Param("memberNum") Long memberNum);

}
