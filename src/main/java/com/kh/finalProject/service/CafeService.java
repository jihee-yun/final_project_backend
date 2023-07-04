package com.kh.finalProject.service;

import com.kh.finalProject.dto.CafeDetailDto;
import com.kh.finalProject.dto.CafeDto;
import com.kh.finalProject.dto.ImgDto;
import com.kh.finalProject.entity.Cafe;
import com.kh.finalProject.entity.CafeImg;
import com.kh.finalProject.entity.CafeMenu;
import com.kh.finalProject.repository.CafeImgRepository;
import com.kh.finalProject.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CafeService {
    private final CafeRepository cafeRepository;
    private final CafeImgRepository cafeImgRepository;

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

    // 인기순 조회
    public List<CafeDto> selectCafeListByPopularity(String region) {
        System.out.println("현재 넘어온 지역 : " + region);
        List<Cafe> cafes;
        if(region.equals("전체")) {
            cafes = cafeRepository.findAllByOrderByLikeCountDesc(); // 전체 카테고리 클릭 시
        } else {
            cafes = cafeRepository.findByRegionOrderByLikeCountDesc(region);
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

    // 별점순 조회
    public List<CafeDto> selectCafeListByScore(String region) {
        System.out.println("현재 넘어온 지역 : " + region);
        List<Cafe> cafes;
        if(region.equals("전체")) {
            cafes = cafeRepository.findAllByOrderByScoreDesc(); // 전체 카테고리 클릭 시
        } else {
            cafes = cafeRepository.findByRegionOrderByScoreDesc(region);
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

    // 카페 디테일 조회(카페 번호로)
    public List<CafeDetailDto> detailCafe(Long cafeNum) {
        System.out.println("현재 넘어온 카페 번호 :" + cafeNum);
        Optional<Cafe> cafe = cafeRepository.findByIdWithDetails(cafeNum);
        System.out.println("카페 값 저장 확인 : " + cafe.isPresent());
        List<CafeDetailDto> cafeDetailDtos = new ArrayList<>();
        if(cafe.isPresent()) {
            Cafe cafe1 = cafe.get();
            System.out.println("Cafe 정보가 존재합니다: " + cafe);
            CafeDetailDto cafeDetailDto = new CafeDetailDto();

            cafeDetailDto.setId(cafe1.getId());
            cafeDetailDto.setCafeName(cafe1.getCafeName());
            cafeDetailDto.setIntro(cafe1.getIntro());
            cafeDetailDto.setDetailIntro(cafe1.getDetailIntro());
            cafeDetailDto.setAddr(cafe1.getAddress());
            cafeDetailDto.setTel(cafe1.getTel());
            cafeDetailDto.setOperatingTime(cafe1.getOperatingTime());
            cafeDetailDto.setMenuList(cafe1.getCafeMenuList()
                    .stream()
                    .map(menu -> menu.getId() + " - " + menu.getName() + " - " + menu.getPrice()) // name과 price를 함께 매핑
                    .collect(Collectors.toList()));
            cafeDetailDtos.add(cafeDetailDto);
        }
        return cafeDetailDtos;
    }

    // 카페 이미지 조회
    public List<ImgDto> imgListGet(Long cafeNum) {
        System.out.println("현재 넘어온 카페 번호 :" + cafeNum);
        List<CafeImg> cafeImgs = cafeImgRepository.findByCafeId(cafeNum);
        List<ImgDto> imgDtos = new ArrayList<>();
        for(CafeImg cafeImg : cafeImgs) {
            ImgDto imgDto = new ImgDto();
            imgDto.setId(cafeImg.getId());
            imgDto.setUrl(cafeImg.getUrl());
            imgDtos.add(imgDto);
        }
        return imgDtos;
    }
}
