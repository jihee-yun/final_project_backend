package com.kh.finalProject.controller;

import com.kh.finalProject.dto.CafeDetailDto;
import com.kh.finalProject.dto.CafeDto;
import com.kh.finalProject.dto.ImgDto;
import com.kh.finalProject.service.CafeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cafe")
@CrossOrigin(origins = "http://localhost:3000")
public class CafeController {
    private final CafeService cafeService;

    @GetMapping("/region")
    public ResponseEntity<List<CafeDto>> selectCafeList(@RequestParam String region) {
        System.out.println("지역 : " + region);
        List<CafeDto> list = cafeService.selectCafeList(region);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<List<CafeDetailDto>> detailCafe(@RequestParam Long cafeNum) {
        System.out.println("카페번호 : " + cafeNum);
        List<CafeDetailDto> list = cafeService.detailCafe(cafeNum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/img")
    public ResponseEntity<List<ImgDto>> imgList(@RequestParam Long cafeNum) {
        System.out.println("카페번호 : " + cafeNum);
        List<ImgDto> list = cafeService.imgListGet(cafeNum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
