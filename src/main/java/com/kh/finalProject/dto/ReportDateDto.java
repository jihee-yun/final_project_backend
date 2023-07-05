package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReportDateDto {
    private String userId;
    private LocalDate startDate;
    private LocalDate endDate;
}
