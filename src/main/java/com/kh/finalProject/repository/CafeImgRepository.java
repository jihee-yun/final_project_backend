package com.kh.finalProject.repository;

import com.kh.finalProject.entity.CafeImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CafeImgRepository extends JpaRepository<CafeImg, Long> {
    List<CafeImg> findByCafeId(Long cafeId);
}
