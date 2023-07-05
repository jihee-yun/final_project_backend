package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReportDto {
    private Long reportNum;
    private String userId; // 작성자
    private String content; // 신고 내용
    private String title; // 제목
    private LocalDateTime reportDate; //신고 날짜
}
