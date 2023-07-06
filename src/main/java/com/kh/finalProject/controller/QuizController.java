package com.kh.finalProject.controller;

import com.kh.finalProject.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/quizmain")
@CrossOrigin(origins = "http://localhost:3000")
public class QuizController {
    public final PointService pointService;

    @PostMapping("/quizpoint")
    public ResponseEntity<Boolean> quizPoint(@RequestBody Map<String, Object> quizItem) {
        int totalPoint = (Integer) quizItem.get("totalPoint");
        log.info("퀴즈 적립 포인트 : " + totalPoint);
        boolean result = pointService.addPoint(totalPoint);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
