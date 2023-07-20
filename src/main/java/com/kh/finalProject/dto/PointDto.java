package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointDto {
    private Long id;
    private int point;
    private Long memberNum;
    private String pointType;
}
