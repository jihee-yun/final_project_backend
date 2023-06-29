package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainRepository extends JpaRepository<Cafe, Long> {
    // 인기카페 조회
    List<Cafe> findAll();
}
