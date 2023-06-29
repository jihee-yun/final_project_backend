package com.kh.finalProject.service;

import com.kh.finalProject.dto.ReviewDto;
import com.kh.finalProject.entity.Review;
import com.kh.finalProject.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<ReviewDto> getReviewList() {
        List<Review> reviewList = reviewRepository.findAll();
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for (Review review : reviewList) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewNum(review.getReviewNum());
            reviewDto.setUserNum(review.getUserNum());
            reviewDto.setCafeNum(review.getCafeNum());
            reviewDto.setReviewContent(review.getReviewContent());
            reviewDto.setReviewImgUrl1(review.getReviewImgUrl1());
            reviewDto.setReviewImgUrl2(review.getReviewImgUrl2());
            reviewDto.setWrittenTime(review.getWrittenTime());
            reviewDto.setLikeCount(review.getLikeCount());
            reviewDto.setScore(review.getScore());
            reviewDtoList.add(reviewDto);
        }
        return reviewDtoList;
    }

    public List<ReviewDto> getReviewListByNum(Long userNum) {
        List<Review> reviewList = reviewRepository.findByUserNum(userNum);
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for (Review review : reviewList) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewNum(review.getReviewNum());
            reviewDto.setUserNum(review.getUserNum());
            reviewDto.setCafeNum(review.getCafeNum());
            reviewDto.setReviewContent(review.getReviewContent());
            reviewDto.setReviewImgUrl1(review.getReviewImgUrl1());
            reviewDto.setReviewImgUrl2(review.getReviewImgUrl2());
            reviewDto.setWrittenTime(review.getWrittenTime());
            reviewDto.setLikeCount(review.getLikeCount());
            reviewDto.setScore(review.getScore());
            reviewDtoList.add(reviewDto);
        }
        return reviewDtoList;
    }

    public List<ReviewDto> getReviewListByNumAndDate(Long userNum, LocalDateTime startDate, LocalDateTime endDate) {
        List<Review> reviewList = reviewRepository.findByUserNumAndWrittenTimeBetween(userNum, startDate, endDate);
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for (Review review : reviewList) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewNum(review.getReviewNum());
            reviewDto.setUserNum(review.getUserNum());
            reviewDto.setCafeNum(review.getCafeNum());
            reviewDto.setReviewContent(review.getReviewContent());
            reviewDto.setReviewImgUrl1(review.getReviewImgUrl1());
            reviewDto.setReviewImgUrl2(review.getReviewImgUrl2());
            reviewDto.setWrittenTime(review.getWrittenTime());
            reviewDto.setLikeCount(review.getLikeCount());
            reviewDto.setScore(review.getScore());
            reviewDtoList.add(reviewDto);
        }
        return reviewDtoList;
    }


}
