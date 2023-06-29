package com.kh.finalProject.controller;

import com.kh.finalProject.dto.ReviewDto;
import com.kh.finalProject.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    // 존재하는 모든 리뷰 조회
    @GetMapping("/getall")
    public ResponseEntity<List<ReviewDto>> reviewListAll() {
        List<ReviewDto> list = reviewService.getReviewList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 유저 ID를 이용하여 해당 유저 리뷰 전체 조회
    @GetMapping("/getbynum")
    public ResponseEntity<List<ReviewDto>> reviewListByNum(@RequestParam Long usernum) {
        List<ReviewDto> list = reviewService.getReviewListByNum(usernum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 유저 ID와 날짜 기간을 이용하여 해당 유저 리뷰 조회
    @PostMapping("/getbynum&date")
    public ResponseEntity<List<ReviewDto>> reviewListByNumAndDate(@RequestBody ReviewDto checkData) {
        Long userNum = checkData.getUserNum();
        LocalDateTime startDate = checkData.getWrittenTime();
        LocalDateTime endDate = checkData.getWrittenTime();
        List<ReviewDto> list = reviewService.getReviewListByNumAndDate(userNum, startDate, endDate);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
