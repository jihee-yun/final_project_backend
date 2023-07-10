package com.kh.finalProject.controller;

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

}
