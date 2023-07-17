package com.kh.finalProject.controller;

import com.kh.finalProject.dto.CafeReviewDto;
import com.kh.finalProject.dto.ReviewDateDto;
import com.kh.finalProject.dto.ReviewDto;
import com.kh.finalProject.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/getall")
    public ResponseEntity<List<ReviewDto>> reviewListAll() {
        List<ReviewDto> list = reviewService.getReviewList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/getbynum")
    public ResponseEntity<List<ReviewDto>> reviewListByNum(@RequestParam Long userNum) {
        List<ReviewDto> list = reviewService.getReviewListByNum(userNum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/getbynumdate")
    public ResponseEntity<List<ReviewDto>> reviewListByNumAndDate(@RequestBody ReviewDateDto checkData) {
        Long userNum = checkData.getUserNum();
        LocalDate startDate = checkData.getStartDate();
        LocalDate endDate = checkData.getEndDate();
        List<ReviewDto> list = reviewService.getReviewListByNumAndDate(userNum, startDate, endDate);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 새로운 리뷰 작성
    @PostMapping("/newReview")
    public ResponseEntity<Boolean> createNewReview(@RequestBody Map<String, String> reviewData) {
        Long memNum = Long.valueOf(reviewData.get("memNum"));
        Long cafeNum = Long.valueOf(reviewData.get("cafeNum"));
        String content = reviewData.get("content");
        double score = Double.parseDouble(reviewData.get("score"));
        String url1 = reviewData.get("url1");
        String url2 = reviewData.get("url2");
        boolean result = reviewService.createNewReview(memNum, cafeNum, content, score, url1, url2);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 특정 카페 리뷰 조회
    @GetMapping("/cafeReview")
    public ResponseEntity<List<CafeReviewDto>> cafeReview(@RequestParam Long cafeNum) {
        System.out.println("넘어온 값 :" + cafeNum);
        List<CafeReviewDto> list = reviewService.cafeReview(cafeNum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 리뷰 수정
    @PostMapping("/edit")
    public ResponseEntity<Boolean> editReview(@RequestBody Map<String, String> reviewData) {
        Long reviewNum = Long.valueOf(reviewData.get("reviewNum"));
        Long cafeNum = Long.valueOf(reviewData.get("cafeNum"));
        String content = reviewData.get("content");
        double score = Double.parseDouble(reviewData.get("editScore"));
        String url1 = reviewData.get("url1");
        String url2 = reviewData.get("url2");
        boolean result = reviewService.editReview(reviewNum, cafeNum, content, score, url1, url2);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 리뷰 삭제
    @PostMapping("/delete")
    public ResponseEntity<Boolean> deleteReview(@RequestBody Map<String, String> reviewData) {
        Long reviewNum = Long.valueOf(reviewData.get("reviewId"));
        Long cafeNum = Long.valueOf(reviewData.get("cafeNum"));
        boolean result = reviewService.deleteReview(reviewNum, cafeNum);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 리뷰 좋아요 기능
    @PostMapping("/like")
    public ResponseEntity<Boolean> changeReviewLike(@RequestBody Map<String, Long> likeData) {
        Long memNum = likeData.get("memNum");
        Long reviewNum = likeData.get("reviewId");
        boolean result = reviewService.changeReviewLike(memNum, reviewNum);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
