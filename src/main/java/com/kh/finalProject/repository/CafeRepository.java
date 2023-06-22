package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeRepository extends JpaRepository<Cafe, Long> {

    // 지역별 카페 조회
    List<Cafe> findByRegion(String region);
}
