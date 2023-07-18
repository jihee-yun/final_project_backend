package com.kh.finalProject.service;

import com.kh.finalProject.dto.CafeDetailDto;
import com.kh.finalProject.dto.CafeDto;
import com.kh.finalProject.dto.ImgDto;
import com.kh.finalProject.entity.*;
import com.kh.finalProject.repository.*;
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
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    private final MemberRepository memberRepository;
    private final CafeLikeRepository cafeLikeRepository;

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
        Optional<Cafe> cafe = cafeRepository.findByIdWithDetails(cafeNum);
        List<CafeDetailDto> cafeDetailDtos = new ArrayList<>();
        List<Review> reviews = reviewRepository.findByCafeNum(cafeNum);
        double totalScore = 0.0;

        for(Review review : reviews) {
            totalScore += review.getScore();
        }

        double avgScore = reviews.isEmpty() ? 0.0 : totalScore / reviews.size();
        double roundedAvgScore = Math.round(avgScore * 10) / 10.0;

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
            cafeDetailDto.setAvgScore(roundedAvgScore);
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

    // 카페 좋아요 기능
    public boolean changeCafeLike(Long cafeNum, Long memNum) {
        Optional<Cafe> cafe = cafeRepository.findById(cafeNum);
        Optional<Member> member = memberRepository.findByMemberNum(memNum);

        if(cafe.isPresent() && member.isPresent()) {
            Optional<CafeLike> cafeLike = cafeLikeRepository.findByUserAndCafe(member.get(), cafe.get());

            if(cafeLike.isPresent()) {
                cafeLikeRepository.delete(cafeLike.get());

                cafeLikeCountUpdate(cafeNum, -1);
                return false;
            } else {
                CafeLike newLike = new CafeLike();
                newLike.setMember(member.get());
                newLike.setCafe(cafe.get());
                cafeLikeRepository.save(newLike);

                cafeLikeCountUpdate(cafeNum, 1);
                return true;
            }
        } else {
            throw new IllegalArgumentException("해당 유저 또는 리뷰를 찾을 수 없습니다.");
        }
    }

    // 카페 좋아요 카운트 반영을 위한 메소드
    public void cafeLikeCountUpdate(Long cafeNum, int count) {
        Optional<Cafe> cafe = cafeRepository.findById(cafeNum);

        if(cafe.isPresent()) {
            Cafe cafe1 = cafe.get();
            Long likeCount = cafe1.getLikeCount();
            cafe1.setLikeCount(likeCount + count);
            cafeRepository.save(cafe1);
        }
    }
}
