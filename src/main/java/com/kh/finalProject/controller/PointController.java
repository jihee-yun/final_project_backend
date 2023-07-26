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
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
@CrossOrigin(origins = "http://localhost:3000")
public class PointController {
    public final PointService pointService;

    // 내 포인트 내역 조회
    @GetMapping("/mypoint")
    public ResponseEntity<List<PointDto>> pointList(@RequestParam Long memberNum) {
        List<PointDto> list = pointService.getPointList(memberNum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 포인트 적립
    @PostMapping("/pointadd")
    public ResponseEntity<Boolean> addPoint(@RequestBody PointDto pointDto) {
        Long memberNum = pointDto.getMemberNum();
        int point = pointDto.getPoint();
        String pointType = pointDto.getPointType();
        boolean result = pointService.addPoints(memberNum, point, pointType);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 기간 내의 포인트 충전, 사용 내역 조회
    @PostMapping("/getbynumdate")
    public ResponseEntity<List<PointDto>> paymentListByNumAndDate(@RequestBody PointListDto checkData) {
        Long memberNum = checkData.getUserNum();
        LocalDate startDate = checkData.getStartDate();
        LocalDate endDate = checkData.getEndDate();
        List<PointDto> list = pointService.getPaymentListByNumAndDate(memberNum, startDate, endDate);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 포인트 충전
    @PostMapping("/chargepoint")
    public ResponseEntity<Boolean> chargePoint(@RequestBody Map<String, Long> list){
        Long memberNum = list.get("userNum");
        Long point = list.get("point");
        boolean result = pointService.chargePointByMemberNum(memberNum, point);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
