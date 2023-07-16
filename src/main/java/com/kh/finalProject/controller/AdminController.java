package com.kh.finalProject.controller;


import com.kh.finalProject.dto.ReportDto;
import com.kh.finalProject.service.AdminService;
import com.kh.finalProject.service.ReportService;
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

    // 신고 전제 조회
    @GetMapping("/report/all")
    public ResponseEntity<List<ReportDto>> reportAll() {
        List<ReportDto> list = reportService.getReportList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 번호로 신고 조회
    @GetMapping("/report/getbynum")
    public ResponseEntity<List<ReportDto>> reportByNum(@RequestParam Long reportNum) {
        List<ReportDto> list = reportService.getAllReportsByReportNum(reportNum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
