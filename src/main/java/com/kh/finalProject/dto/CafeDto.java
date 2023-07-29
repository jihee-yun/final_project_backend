package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CafeDto {
    private Long id;
    private String cafeName;
    private String region;
    private String intro;
    private String thumbnail;
    private List<Map<String, Object>> cafeList; // 검색용 카페 리스트 추가
}
