package com.kh.finalProject.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_review")
@Getter @Setter
@NoArgsConstructor // 빌더용(안쓰면 없어도 됨)
public class Review {
    @Id
    @Column(name = "review_num_pk")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewNum; // 리뷰 구분용 ID
    private Long userNum; // 리뷰 작성자 ID (일반 회원)
    private Long cafeNum; // 리뷰 대상 카페 ID
    @Lob // 문자열 길이 증가
    @Column(nullable = false, length = 2000) // null 방지
    private String reviewContent; // 리뷰 내용
    @Column(length = 2000)
    private String reviewImgUrl1; // 이미지 url 1
    @Column(length = 2000)
    private String reviewImgUrl2; // 이미지 url 2
    private LocalDate writtenTime; // 글 작성일
    private int likeCount; // 좋아요 카운트
    private double score; // 리뷰 점수
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewLike> reviewLikeList = new ArrayList<>();


}
