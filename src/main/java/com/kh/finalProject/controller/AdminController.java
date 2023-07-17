package com.kh.finalProject.controller;


import com.kh.finalProject.dto.ReportDto;
import com.kh.finalProject.dto.ReviewDto;
import com.kh.finalProject.dto.UserDto;
import com.kh.finalProject.service.ReportService;
import com.kh.finalProject.service.ReviewService;
import com.kh.finalProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin")
public class AdminController {
    private final ReportService reportService;
    private final UserService userService;
    private final ReviewService reviewService;

    // 전체 회원 조회
    @GetMapping("/usermanage")
    public ResponseEntity<List<UserDto>> userAll() {
        List<UserDto> list = userService.getMemberList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 신고 전제 조회
    @GetMapping("/report/all")
    public ResponseEntity<List<ReportDto>> reportAll() {
        List<ReportDto> list = reportService.getReportList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 리뷰 전체 조회
    @GetMapping("/review/all")
    public ResponseEntity<List<ReviewDto>> reviewAll() {
        List<ReviewDto> list = reviewService.getAdminReview();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // userNum 리뷰 조회
    @GetMapping("/review/getbynum")
    public ResponseEntity<List<ReviewDto>> reviewByNum(@RequestParam Long userNum) {
        List<ReviewDto> list = reviewService.getAdminReviewNum(userNum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 번호로 신고 조회
    @GetMapping("/report/getbynum")
    public ResponseEntity<List<ReportDto>> reportByNum(@RequestParam Long reportNum) {
        List<ReportDto> list = reportService.getAllReportsByReportNum(reportNum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 신고 내용 보기
    @GetMapping("/report/getContents")
    public ResponseEntity<List<ReportDto>> getReportContent(@RequestParam Long reportNum) {
        List<ReportDto> list = reportService.getReportContent(reportNum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
