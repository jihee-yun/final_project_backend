package com.kh.finalProject.controller;

import com.kh.finalProject.dto.ReviewDto;
import com.kh.finalProject.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/getall")
    public ResponseEntity<List<ReviewDto>> reviewListAll() {
        List<ReviewDto> list = reviewService.getReviewList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/getbyid")
    public ResponseEntity<List<ReviewDto>> reviewListByNum(@RequestParam Long usernum) {
        List<ReviewDto> list = reviewService.getReviewListByNum(usernum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
