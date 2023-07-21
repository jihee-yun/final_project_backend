package com.kh.finalProject.controller;

import com.kh.finalProject.dto.PointDto;
import com.kh.finalProject.dto.PointListDto;
import com.kh.finalProject.service.PointService;
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
@RequestMapping("/event")
public class EventController {
    private final PointService pointService;

    // 회원번호와 시작, 종료 날짜 3가지를 받아 기간 내의 이벤트 내역 조회
    @PostMapping("/getbynumdate")
    public ResponseEntity<List<PointDto>> pointListByNumAndDate(@RequestBody PointListDto checkData) {
        Long memberNum = checkData.getUserNum();
        LocalDate startDate = checkData.getStartDate();
        LocalDate endDate = checkData.getEndDate();
        List<PointDto> list = pointService.getPointListByNumAndDate(memberNum, startDate, endDate);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
