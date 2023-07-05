package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findByTotalPoint (int totalPoint);
}
