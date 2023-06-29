package com.kh.finalProject.controller;

import com.kh.finalProject.dto.CafeDetailDto;
import com.kh.finalProject.dto.CafeDto;
import com.kh.finalProject.dto.MainDto;
import com.kh.finalProject.entity.Cafe;
import com.kh.finalProject.repository.MainRepository;
import com.kh.finalProject.service.CafeService;
import com.kh.finalProject.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth/main")
@CrossOrigin(origins = "http://localhost:3000")
public class MainController {
    private final MainRepository mainRepository;
    private final MainService mainService;
    @GetMapping("/rankingcard")
    public ResponseEntity<List<MainDto>> RankingCard() {
        log.info("ranking card 컨트롤러 진입");
        return ResponseEntity.ok(mainService.mainCafe());
    }
}
