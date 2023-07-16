package com.kh.finalProject.controller;

import com.kh.finalProject.dto.ReportDateDto;
import com.kh.finalProject.dto.ReportDto;
import com.kh.finalProject.service.ReportService;
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
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin/report")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/all")
    public ResponseEntity<List<ReportDto>> reportAll() {
        List<ReportDto> list = reportService.getReportList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 번호로 신고 조회
    @GetMapping("/getbynum")
    public ResponseEntity<List<ReportDto>> reportByNum(@RequestParam Long reportNum) {
        List<ReportDto> list = reportService.getAllReportsByReportNum(reportNum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/getDate")
    public ResponseEntity<List<ReportDto>> reportByNumAndDate(@RequestParam ReportDateDto reportDateDto) {
        String userId = reportDateDto.getUserId();
        LocalDate startDate = reportDateDto.getStartDate();
        LocalDate endDate = reportDateDto.getEndDate();
        List<ReportDto> list = reportService.getReportByNumAndDate(userId, startDate, endDate);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
