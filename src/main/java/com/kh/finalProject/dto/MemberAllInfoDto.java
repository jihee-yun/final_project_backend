package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class MemberAllInfoDto {
    private Long memberNum; // 회원 번호
    private List<String> reviewContents; // 리뷰 내용
    private List<String> guildNames; // 길드 이름
    private List<String> challengeNames; // 챌린지 이름
    private List<String> pointTypes; // 이벤트 적립 내역
    private List<String> paymentTypes; // 포인트 사용, 충전 내역
    private List<String> reportTitles; // 문의, 신고 제목
}
