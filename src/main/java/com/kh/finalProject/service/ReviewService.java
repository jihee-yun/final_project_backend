package com.kh.finalProject.service;

import com.kh.finalProject.dto.CafeReviewDto;
import com.kh.finalProject.dto.ReviewDto;
import com.kh.finalProject.entity.Cafe;
import com.kh.finalProject.entity.Review;
import com.kh.finalProject.entity.User;
import com.kh.finalProject.repository.CafeRepository;
import com.kh.finalProject.repository.ReviewRepository;
import com.kh.finalProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final CafeRepository cafeRepository;

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

    public List<ReviewDto> getReviewListByNumAndDate(Long userNum, LocalDate startDate, LocalDate endDate) {
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

    // 새로운 리뷰 작성
    public boolean createNewReview(Long memNum, Long cafeNum, String content, double score, String url1, String url2) {
        Optional<User> user = userRepository.findByUserNum(memNum);
        Optional<Cafe> cafe = cafeRepository.findById(cafeNum);
        if (user.isPresent() && cafe.isPresent()) {
            Review review = new Review();
            review.setUserNum(user.get().getUserNum());
            review.setCafeNum(cafe.get().getId());
            review.setReviewContent(content);
            review.setReviewImgUrl1(url1);
            review.setReviewImgUrl2(url2);
            review.setScore(score);
            review.setLikeCount(0);
            review.setWrittenTime(LocalDate.now());
            Review newReview = reviewRepository.save(review);
            return true;
        } else {
            throw new IllegalArgumentException("해당 유저를 찾을 수 없습니다. ");
        }
    }

    // 특정 카페 리뷰 조회
    public List<CafeReviewDto> cafeReview(Long cafeNum) {
        List<Review> reviews = reviewRepository.findByCafeNum(cafeNum);
        List<CafeReviewDto> cafeReviewDtos = new ArrayList<>();
        double totalScore = 0.0;

        for (Review review : reviews) {
            Long memNum = review.getUserNum();
            Optional<User> user = userRepository.findByUserNum(memNum);

            if (user.isPresent()) {
                User userData = user.get();
                CafeReviewDto cafeReviewDto = new CafeReviewDto();
                cafeReviewDto.setId(review.getReviewNum());
                cafeReviewDto.setUserNum(userData.getUserNum());
                cafeReviewDto.setUserId(userData.getUserId());
                cafeReviewDto.setProfile(userData.getProfileImgUrl());
                cafeReviewDto.setContent(review.getReviewContent());
                cafeReviewDto.setUrl1(review.getReviewImgUrl1());
                cafeReviewDto.setUrl2(review.getReviewImgUrl2());
                cafeReviewDto.setLikeCount(review.getLikeCount());
                cafeReviewDto.setScore(review.getScore());
                totalScore += review.getScore();
                cafeReviewDto.setCountReview(reviews.size());
                cafeReviewDto.setWrittenDay(review.getWrittenTime());
                cafeReviewDtos.add(cafeReviewDto);
            }
        }
        double avgScore = cafeReviewDtos.isEmpty() ? 0.0 : totalScore / cafeReviewDtos.size();
        double roundedAvgScore = Math.round(avgScore * 10) / 10.0;

        for (CafeReviewDto cafeReviewDto : cafeReviewDtos) {
            cafeReviewDto.setAvgScore(roundedAvgScore);
        }
        return cafeReviewDtos;
    }
}
