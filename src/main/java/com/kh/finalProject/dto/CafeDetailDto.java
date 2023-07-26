package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CafeDetailDto {
    private Long id;
    private String cafeName;
    private String intro;
    private String detailIntro;
    private String addr;
    private String tel;
    private String operatingTime;
    private double avgScore;
    private List<String> imgList; // 상품 이미지 리스트
    private List<String> menuList; // 가게 대표 메뉴 리스트
    private List<Map<String, Object>> cafeList; // 검색용 카페 리스트 추가
}
