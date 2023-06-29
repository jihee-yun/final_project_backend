package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {

    // 지역별 카페 조회
    List<Cafe> findByRegion(String region);

    // 카페 디테일 정보 조회
    @Query("SELECT c FROM Cafe c LEFT JOIN FETCH c.cafeMenuList cm WHERE c.id = :cafe_id")
    Optional<Cafe> findByIdWithDetails(@Param("cafe_id") Long cafeId);
}
