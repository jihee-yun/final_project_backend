package com.kh.finalProject.controller;

import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.Roulette;
import com.kh.finalProject.service.PointService;
import com.kh.finalProject.service.RouletteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/roulette")
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
public class RouletteController {
    private final RouletteService rouletteService;

    @PostMapping("/spin")
    public ResponseEntity<Boolean> spinRoulette(@RequestBody Map<String, Long> userData) {
        Long member = userData.get("memberNum");
        boolean result = rouletteService.spinRoulette(member);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/history")
    public ResponseEntity<Boolean> checkHistory(@RequestBody Map<String, String> checkData) {
        Long memNum = Long.valueOf(checkData.get("memberNum"));
        boolean result = rouletteService.checkHistory(memNum);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
