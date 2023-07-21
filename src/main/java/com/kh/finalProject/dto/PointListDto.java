package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PointListDto {
    private Long id; // 포인트 내역 primary Id
    private int point; // 포인트 변동 숫자
    private Long userNum; // 회원 번호
    private Long memberNum; // 회원 번호
    private String pointType; // 포인트 변동 내역
    private LocalDate startDate; // 검색 시작 날짜
    private LocalDate endDate; // 검색 종료 날짜
}
