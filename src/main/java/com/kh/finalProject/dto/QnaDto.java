package com.kh.finalProject.dto;

import com.kh.finalProject.constant.QnaCategory;
import lombok.Builder;
import lombok.Getter;


@Getter @Builder

public class QnaDto {
    private String question;
    private String answer;
    private QnaCategory category;

}
