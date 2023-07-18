package com.kh.finalProject.controller;

import com.kh.finalProject.dto.MyChallengeDto;
import com.kh.finalProject.service.ChallengeService;
import com.kh.finalProject.service.MyChallengeService;
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
@RequestMapping("/mychallenge")
@CrossOrigin(origins = "http://localhost:3000")
public class MyChallengeController {
    private final MyChallengeService myChallengeService;
    private final ChallengeService challengeService;

    // 마이챌린지 조회
    @GetMapping("/get")
    public ResponseEntity<List<MyChallengeDto>> MyChallengeList(@RequestParam Long memberNum, Long challengeId) {
        List<MyChallengeDto> list = myChallengeService.getMyChallengeList(memberNum, challengeId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 챌린지 신청
    @PostMapping("/apply")
    public ResponseEntity<Boolean> applyChallenge(@RequestBody Map<String, Long> list) {
        Long challengeId = list.get("challengeId");
        Long memberId = list.get("memberId");
        boolean result = challengeService.applyChallenge(challengeId, memberId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
