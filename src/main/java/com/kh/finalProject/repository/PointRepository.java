package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findByMember (Member member);
    List<Point> findByMemberMemberNum(Long memberNum);
    // 회원 객체로 내역 조회
    @Query("SELECT p.pointType FROM Point p WHERE p.member = :member")
    List<String> findPointTypeByMember(@Param("member") Member member);
    // 회원 객체와 시작~종료 날짜 사이의 포인트 테이블 조회
    List<Point> findByMemberAndPointDateBetween(Member member, LocalDate startDate, LocalDate endDate);

}
