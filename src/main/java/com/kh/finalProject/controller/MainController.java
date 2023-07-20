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
import org.jboss.jandex.Main;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/main")
@CrossOrigin(origins = "http://localhost:3000")
public class MainController {
    private final MainRepository mainRepository;
    private final MainService mainService;
    //랭킹카드에 카페정보 불러오기
    @GetMapping("/rankingcard")
    public ResponseEntity<List<MainDto>> RankingCard() {
        log.info("ranking card 컨트롤러 진입");
        List<MainDto> list = mainService.mainCafe();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
