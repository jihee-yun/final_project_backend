package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ChallengeDto {
    private Long id;
    private String challengeName;
    private String thumbnail;
    private String detail;
    private int count;
//    private Long myChallengeId;
//    private LocalDateTime startTime;
    private LocalDate endTime;
}
