package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserNum(Long userNum);
    // 회원 번호로 리뷰 내용만 문자열로 추출 후 리스트에 담아 반환
    @Query("select r.reviewContent from Review r where r.userNum = ?1")
    List<String> findReviewContentByUserNum(Long userNum);
    List<Review> findByReviewNum(Long reviewNum);
    List<Review> findByUserNumAndWrittenTimeBetween(Long userNum, LocalDate startDate, LocalDate endDate);
    // 회원 번호로 등록된 카페 조회 후 해당 카페의 리뷰를 두 기간 사이로 조회함
    List<Review> findByCafeNumAndWrittenTimeBetween(Long userNum, LocalDate startDate, LocalDate endDate);

    List<Review> findByCafeNum(Long cafeNum);

    List<Review> findByCafeNumOrderByReviewNumDesc(Long cafeNum);

    List<Review> findByCafeNumOrderByLikeCountDesc(Long cafeNum);

}