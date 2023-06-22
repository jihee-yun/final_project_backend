package com.kh.finalProject.service;

import com.kh.finalProject.dto.CafeDto;
import com.kh.finalProject.entity.Cafe;
import com.kh.finalProject.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CafeService {
    private final CafeRepository cafeRepository;

    // 지역별(카테고리 선택별) 카페 조회
    public List<CafeDto> selectCafeList(String region) {
        List<Cafe> cafes;
        if(region.equals("전체")) {
            cafes = cafeRepository.findAll(); // 전체 카테고리 클릭 시
        } else {
            cafes = cafeRepository.findByRegion(region);
        }
        List<CafeDto> cafeDtos = new ArrayList<>();
        for(Cafe cafe : cafes) {
            CafeDto cafeDto = new CafeDto();
            cafeDto.setCafeName(cafe.getCafeName());
            cafeDto.setId(cafe.getId());
            cafeDto.setIntro(cafe.getIntro());
            cafeDto.setRegion(cafe.getRegion());
            cafeDto.setThumbnail(cafe.getThumbnail());
            cafeDtos.add(cafeDto);
        }
        return cafeDtos;
    }
}
