package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ReviewDateDto {
    private Long userNum; // 리뷰 작성자 ID (일반 회원)
    private LocalDate startDate; // 검색 시작 날짜
    private LocalDate endDate; // 검색 종료 날짜
}
