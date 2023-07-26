package com.kh.finalProject.controller;

import com.kh.finalProject.dto.MyCouponDto;
import com.kh.finalProject.service.CouponService;
import com.kh.finalProject.service.MyCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mycoupon")
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
public class MyCouponController {
    private final MyCouponService myCouponService;
    private final CouponService couponService;

    // 내 쿠폰 조회
    @GetMapping("/getcoupon")
    public ResponseEntity<List<MyCouponDto>> myCouponList(@RequestParam Long memberNum, Long couponId) {
        List<MyCouponDto> list = myCouponService.myCouponList(memberNum, couponId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
