package com.kh.finalProject.dto;

import com.kh.finalProject.constant.QnaCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;


@Getter @Setter

public class QnaDto {
    private String question;
    private String answer;
    private QnaCategory category;
    private List<Map<String, Object>> qnaList; // 검색용리스트 추가

}
