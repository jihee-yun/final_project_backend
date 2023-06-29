package com.kh.finalProject.repository;

import com.kh.finalProject.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserNum(Long userNum);
    List<Review> findByUserNumAndWrittenTimeBetween(Long userNum, LocalDateTime startDate, LocalDateTime endDate);

}
