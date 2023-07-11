package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChallengeDto {
    private Long id;
    private String challengeName;
    private String thumbnail;
    private String detail;
    private int count;
//    private LocalDateTime startTime;
//    private LocalDateTime endTime; // 보류..
}
