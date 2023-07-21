package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PointDto {
    private Long id;
    private int point;
    private Long memberNum;
    private String pointType;
    private LocalDate pointDate;
}
