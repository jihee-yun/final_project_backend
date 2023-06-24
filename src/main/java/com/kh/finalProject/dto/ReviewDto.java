package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ReviewDto {
    private Long reviewNum; // 리뷰 구분용 ID
    private Long userNum; // 리뷰 작성자 ID (일반 회원)
    private Long cafeNum; // 리뷰 대상 카페 ID
    private String reviewContent; // 리뷰 내용
    private String reviewImgUrl1; // 이미지 url 1
    private String reviewImgUrl2; // 이미지 url 2
    private LocalDateTime writtenTime; // 글 작성일
    private int likeCount; // 좋아요 카운트
    private double score; // 리뷰 점수
}
