package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Cafe;
import com.kh.finalProject.entity.CafeLike;
import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CafeLikeRepository extends JpaRepository<CafeLike, Long> {
    Optional<CafeLike> findByMemberAndCafe(Member member, Cafe cafe);
}
