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
    public ResponseEntity<List<ReviewDto>> reviewListByNum(@RequestParam Long usernum) {
        List<ReviewDto> list = reviewService.getReviewListByNum(usernum);
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
}
