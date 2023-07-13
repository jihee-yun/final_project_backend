package com.kh.finalProject.controller;

import com.kh.finalProject.dto.MyChallengeDto;
import com.kh.finalProject.service.MyChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/couponpayment")
@CrossOrigin(origins = "http://localhost:3000")
public class MyChallengeController {
    private final MyChallengeService myChallengeService;

    @GetMapping("/mychallenge")
    public ResponseEntity<List<MyChallengeDto>> MyChallengeList(@RequestParam Long userNum) {
        List<MyChallengeDto> list = myChallengeService.getMyChallengeList(userNum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
