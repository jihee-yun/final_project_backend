package com.kh.finalProject.controller;

import com.kh.finalProject.dto.ChallengeDto;
import com.kh.finalProject.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
@CrossOrigin(origins = "http://localhost:3000")
public class ChallengeController {
    private final ChallengeService challengeService;

    @GetMapping("/chList")
    public ResponseEntity<List<ChallengeDto>> challengeList(@RequestParam String chList) {
        System.out.println("chList : " + chList);
        List<ChallengeDto> list = challengeService.getChallengeList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
