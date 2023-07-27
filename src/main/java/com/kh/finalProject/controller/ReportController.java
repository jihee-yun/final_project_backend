package com.kh.finalProject.controller;


import com.kh.finalProject.constant.QnaCategory;
import com.kh.finalProject.constant.RequestCategory;
import com.kh.finalProject.dto.ReportDto;
import com.kh.finalProject.entity.Report;
import com.kh.finalProject.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/register")
    public String registerReport(@RequestParam("userId") String userId,
                                 @RequestParam("title") String title,
                                 @RequestParam("content") String content) {
        ReportDto reportDto = new ReportDto();
        reportDto.setUserId(userId);
        reportDto.setTitle(title);
        reportDto.setContent(content);

        reportService.saveReport(reportDto);

        // 등록 완료 후, 리다이렉트 등의 동작을 수행할 수 있습니다.
        return "redirect:/report";
    }

    // 고객센터 문의사항 등록
    @PostMapping("/newQuestion")
    public ResponseEntity<Boolean> regQuestion(@RequestBody Map<String, String> requestData) {
        Long userNum = Long.valueOf(requestData.get("userNum"));
        String title = requestData.get("title");
        String content = requestData.get("content");
        QnaCategory qnaCategory = QnaCategory.valueOf(requestData.get("userType"));
        RequestCategory requestCategory = RequestCategory.valueOf(requestData.get("category"));
        boolean result = reportService.regQuestion(userNum, title, content, qnaCategory, requestCategory);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
