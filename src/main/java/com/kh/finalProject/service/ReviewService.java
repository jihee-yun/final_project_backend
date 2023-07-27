package com.kh.finalProject.service;

import com.kh.finalProject.dto.CafeReviewDto;
import com.kh.finalProject.dto.ReviewDto;
import com.kh.finalProject.entity.*;
import com.kh.finalProject.repository.*;
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
    private final MemberRepository memberRepository;
    private final CafeRepository cafeRepository;
    private final ReviewLikeRepository reviewLikeRepository;

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

    // 관리자 리뷰관리
    public List<ReviewDto> getAdminReview() {
        List<Review> adminReview = reviewRepository.findAll();
        List<ReviewDto> adminReviewList = new ArrayList<>();
        for(Review review : adminReview) {
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
            adminReviewList.add(reviewDto);
        }
        return adminReviewList;
    }

    // 관리자 리뷰번호
    public List<ReviewDto> getAdminReviewNum(Long reviewNum) {
        List<Review> adminReviewNum = reviewRepository.findByReviewNum(reviewNum);
        List<ReviewDto> adminReviewNumList = new ArrayList<>();
        for(Review review : adminReviewNum) {
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
            adminReviewNumList.add(reviewDto);
        }
            return adminReviewNumList;
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
    // 시작 날짜와 종료 날짜로 리뷰 검색
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
        Optional<Member> member = memberRepository.findByMemberNum(memNum);
        Optional<Cafe> cafe = cafeRepository.findById(cafeNum);
        if (member.isPresent() && cafe.isPresent()) {
            Review review = new Review();
            review.setUserNum(member.get().getMemberNum());
            review.setCafeNum(cafe.get().getId());
            review.setReviewContent(content);
            review.setReviewImgUrl1(url1);
            review.setReviewImgUrl2(url2);
            review.setScore(score);
            review.setLikeCount(0);
            review.setWrittenTime(LocalDate.now());
            Review newReview = reviewRepository.save(review);

            updateAvgScore(cafeNum); // 리뷰 작성이 완료된 후 카페 테이블에 스코어 업데이트

            return true;
        } else {
            throw new IllegalArgumentException("해당 유저를 찾을 수 없습니다. ");
        }
    }

    // 리뷰 수정
    public boolean editReview(Long reviewNum, Long cafeNum, String content, double score, String url1, String url2) {
        Optional<Review> review = reviewRepository.findById(reviewNum);
        if(review.isPresent()) {
            Review editReview = review.get();
            editReview.setReviewContent(content);
            editReview.setScore(score);
            editReview.setReviewImgUrl1(url1);
            editReview.setReviewImgUrl2(url2);
            editReview.setWrittenTime(LocalDate.now());
            Review update = reviewRepository.save(editReview);

            updateAvgScore(cafeNum);
            return true;
        } else {
            throw new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다.");
        }
    }

    // 리뷰 삭제
    public boolean deleteReview(Long reviewNum, Long cafeNum) {
        Optional<Review> review = reviewRepository.findById(reviewNum);
        if(review.isPresent()) {
            Review deleteReview = review.get();
            reviewRepository.delete(deleteReview);

            updateAvgScore(cafeNum);
            return true;
        } else return false;
    }

    // 카페 테이블 스코어 값 업데이트를 위한 메소드
    public void updateAvgScore(Long cafeNum) {
        List<Review> reviews = reviewRepository.findByCafeNum(cafeNum);
        int reviewCount = reviews.size();
        double totalScore = 0.0;

        for(Review review : reviews) {
            totalScore += review.getScore();
        }

        double avgScore;
        if (reviewCount > 0) {
            avgScore = Math.round((totalScore / reviewCount) * 10) / 10.0;
        } else {
            avgScore = 0.0; // 리뷰 수가 0인 경우 0으로 업데이트
        }

        Optional<Cafe> cafe = cafeRepository.findById(cafeNum);
        if(cafe.isPresent()) {
            Cafe cafe1 = cafe.get();
            cafe1.setScore(avgScore);
            cafeRepository.save(cafe1);
        }
    }

    // 특정 카페 리뷰 조회
    public List<CafeReviewDto> cafeReview(Long cafeNum, String category) {
        List<Review> reviews;

        if(category.equals("최신순")) {
            reviews = reviewRepository.findByCafeNumOrderByWrittenTimeDesc(cafeNum);
        } else if(category.equals("좋아요순")) {
            reviews = reviewRepository.findByCafeNumOrderByLikeCountDesc(cafeNum);
        } else {
            throw new IllegalArgumentException("잘못된 카테고리입니다.");
        }

        List<CafeReviewDto> cafeReviewDtos = new ArrayList<>();
        double totalScore = 0.0;

        for (Review review : reviews) {
            Long memNum = review.getUserNum();
            Optional<Member> member = memberRepository.findByMemberNum(memNum);

            if (member.isPresent()) {
                Member userData = member.get();
                CafeReviewDto cafeReviewDto = new CafeReviewDto();
                cafeReviewDto.setId(review.getReviewNum());
                cafeReviewDto.setUserNum(userData.getMemberNum());
                cafeReviewDto.setUserId(userData.getMemberId());
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


    // 리뷰 좋아요 기능
    public boolean changeReviewLike(Long memNum, Long reviewNum) {
        Optional<Member> member = memberRepository.findByMemberNum(memNum);
        Optional<Review> review = reviewRepository.findById(reviewNum);

        if(member.isPresent() && review.isPresent()) {
            Optional<ReviewLike> reviewLike = reviewLikeRepository.findByMemberAndReview(member.get(), review.get());

            if(reviewLike.isPresent()) {
                reviewLikeRepository.delete(reviewLike.get());

                reviewLikeCountUpdate(reviewNum, -1);
                return false;
            } else {
                ReviewLike newLike = new ReviewLike();
                newLike.setMember(member.get());
                newLike.setReview(review.get());
                reviewLikeRepository.save(newLike);

                reviewLikeCountUpdate(reviewNum, 1);
                return true;
            }
        } else {
            throw new IllegalArgumentException("해당 유저 또는 리뷰를 찾을 수 없습니다.");
        }
    }

    // 리뷰 좋아요 카운트 반영을 위한 메소드
    public void reviewLikeCountUpdate(Long reviewNum, int count) {
        Optional<Review> review = reviewRepository.findById(reviewNum);

        if(review.isPresent()) {
            Review review1 = review.get();
            int likeCount = review1.getLikeCount();
            review1.setLikeCount(likeCount + count);
            reviewRepository.save(review1);
        }
    }
}
