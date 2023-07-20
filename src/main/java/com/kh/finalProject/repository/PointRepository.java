package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findByMember (Member member);
    List<Point> findByMemberMemberNum(Long memberNum);

}
