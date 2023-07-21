package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findByMember (Member member);
    List<Point> findByMemberMemberNum(Long memberNum);
    // 회원 번호와 시작~종료 날짜 사이의 포인트 테이블 조회
    List<Point> findByMemberAndPointDateBetween(Member member, LocalDate startDate, LocalDate endDate);

}
