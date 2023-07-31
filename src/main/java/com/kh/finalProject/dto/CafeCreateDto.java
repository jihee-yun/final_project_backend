package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CafeCreateDto {
    private Long id;
    private Long memberNum;
    private String cafeName;
    private String region; // 지역
    private String address; // 주소
    private String operatingTime; // 운영 시간
    private String tel; // 전화번호
    private String intro; // 한 줄 소개
    private String detailIntro; // 자세한 소개
    private String thumbnail; // 썸네일 url
}
