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
@RequestMapping("/roulette")
@CrossOrigin(origins = "http://localhost:3000")
public class RouletteController {
    public final PointService pointService;

    @PostMapping("/pointadd")
    public ResponseEntity<Boolean> addPoint(@RequestBody Map<String, Object> pointItem) {
        int totalPoint = (Integer) pointItem.get("totalPoint");
        log.info("받은 포인트 : " + totalPoint);
        boolean result = pointService.addPoint(totalPoint);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
