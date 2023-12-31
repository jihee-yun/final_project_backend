package com.kh.finalProject.controller;

import com.kh.finalProject.dto.CouponDto;
import com.kh.finalProject.dto.CouponPaymentDto;
import com.kh.finalProject.dto.MemberDto;
import com.kh.finalProject.dto.UserDto;
import com.kh.finalProject.service.CouponService;
import com.kh.finalProject.service.MemberService;
import com.kh.finalProject.service.UserService;
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
@RequestMapping("/couponstore")
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
public class CouponController {
    public final CouponService couponService;
    public final UserService userService;

    @GetMapping("/couponget")
    public ResponseEntity<List<CouponDto>> couponList(@RequestParam String couponget) {
        System.out.println("couponget" + couponget);
        List<CouponDto> list = couponService.selectCouponList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

//    @GetMapping("/myinfo")
//    public ResponseEntity<List<UserDto>> infoList(@RequestParam String myinfo) {
//        System.out.println("myinfo" + myinfo);
//        List<UserDto> list = userService.getMemberList();
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }

    @PostMapping("/couponpay")
    public ResponseEntity<Boolean> couponPayment (@RequestBody Map<String, Long> request) {
        Long memberNum = request.get("memberNum");
        Long couponId = request.get("couponId");
        boolean result = couponService.couponPayment(memberNum, couponId);
        return ResponseEntity.ok(result);
    }
}
