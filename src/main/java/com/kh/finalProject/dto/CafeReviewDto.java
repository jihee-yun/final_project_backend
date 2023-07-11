package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CafeReviewDto {
    private Long id; // 리뷰 고유번호
    private Long userNum;
    private String userId;

    private String profile;
    private String content;
    private String url1;
    private String url2;
    private double score;
    private int likeCount;
    private double avgScore; // 평균 별점
    private int countReview; // 리뷰 개수
    private LocalDate writtenDay;
}
