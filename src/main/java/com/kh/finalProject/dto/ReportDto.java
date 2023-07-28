package com.kh.finalProject.dto;

import com.kh.finalProject.constant.QnaCategory;
import com.kh.finalProject.constant.RequestCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReportDto {
    private Long reportNum;
    private String userId; // 작성자
    private String content; // 신고 내용
    private String title; // 제목
    private LocalDate reportDate; //신고 날짜
    private RequestCategory category; // 신고유형
    private QnaCategory memberType; // 회원유형
}
