package com.kh.finalProject.controller;

import com.kh.finalProject.constant.QnaCategory;
import com.kh.finalProject.dto.CafeDetailDto;
import com.kh.finalProject.dto.QnaDto;
import com.kh.finalProject.entity.QnaList;
import com.kh.finalProject.repository.QnaRepository;
import com.kh.finalProject.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth/qnalist")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")

public class QnaController {
    private final QnaRepository qnaRepository;
    private final QnaService qnaService;

    @GetMapping("/get-qna")
    public ResponseEntity<List<QnaList>> getQna(@RequestParam QnaCategory category) {
        log.info("qna 컨트롤러 진입");
        List<QnaList> qnaDtos = qnaRepository.findByCategory(category);
        return new ResponseEntity<>(qnaDtos, HttpStatus.OK);
    }

    // 검색 키워드에 해당하는 리스트 불러오기
    @GetMapping("/searchList")
    public ResponseEntity<List<QnaDto>> qnaSearchListLoad(@RequestParam String keyword) {
        List<QnaDto> qnaList = qnaService.searchDataLoad(keyword);
        return new ResponseEntity<>(qnaList, HttpStatus.OK);
    }
}
