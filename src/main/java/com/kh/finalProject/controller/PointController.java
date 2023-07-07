package com.kh.finalProject.controller;

import com.kh.finalProject.dto.PointDto;
import com.kh.finalProject.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
@CrossOrigin(origins = "http://localhost:3000")
public class PointController {
    public final PointService pointService;

    // 내 포인트 조회
    @GetMapping("/mypoint")
    public ResponseEntity<List<PointDto>> pointList(@RequestParam String mypoint) {
        List<PointDto> list = pointService.getPointList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
