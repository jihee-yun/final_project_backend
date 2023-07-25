package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAll();
    Optional<Member> findByEmail(String email); // Optional -> wrapper 클래스(널값 방지해주는 역할)
    Optional<Member> findByMemberId(String memberId);
    Optional<Member> findByNameAndEmail(String name, String Email);
    Optional<Member> findByMemberNum(Long memberNum);
    Optional<Member> findByNameAndPhoneAndEmail(String name, String phone, String email);
    Optional<Member> findByMemberIdAndPassword(String memberId, String password);
    boolean existsByMemberId(String memberId);
}
