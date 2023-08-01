package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class BusinessMemberAllInfoDto {
    private Long memberNum; // 회원 번호
    private List<String> cafeNames; // 카페 이름 목록
    private List<String> reviewContents; // 리뷰 내용
    private List<String> reportTitles; // 문의, 신고 제목
}
