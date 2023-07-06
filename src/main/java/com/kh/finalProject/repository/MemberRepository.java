package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email); // Optional -> wrapper 클래스(널값 방지해주는 역할)
    Optional<Member> findByMemberId(String memberId);
    Optional<Member> findByMemberIdAndPassword(String memberId, String password);
    boolean existsByMemberId(String memberId);
}
