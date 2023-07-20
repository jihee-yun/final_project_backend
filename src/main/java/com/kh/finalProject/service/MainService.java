package com.kh.finalProject.service;

import com.kh.finalProject.dto.CafeDto;
import com.kh.finalProject.dto.MainDto;
import com.kh.finalProject.entity.Cafe;
import com.kh.finalProject.entity.CafeImg;
import com.kh.finalProject.repository.CafeImgRepository;
import com.kh.finalProject.repository.MainRepository;
import com.kh.finalProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainService {
    private final MainRepository mainRepository;
    private final CafeImgRepository cafeImgRepository;

    // 카페정보 가져오기
    public List<MainDto> mainCafe() {
        List<Cafe> cafe = mainRepository.findAll();
        log.info("cafe = {}", cafe);
        List<MainDto> mainDto = new ArrayList<>();
        int count= 0;



        for(Cafe c : cafe) {
            Collections.shuffle(mainDto);
            if (count >= 6) {
                break; // 4개의 데이터를 가져왔으면 반복문 종료
            }

            MainDto tmp = new MainDto();
            tmp.setId(c.getId());
            List<CafeImg> cafeImgs = cafeImgRepository.findByCafeId(tmp.getId());
            if(!cafeImgs.isEmpty()) {
                tmp.setThumbnail(cafeImgs.get(0).getUrl());
            }

            tmp.setCafeName(c.getCafeName());
            tmp.setIntro(c.getIntro());
            mainDto.add(tmp);

            count++; // 데이터를 가져올 때마다 카운트 증가

            }


        return mainDto;
    }
}