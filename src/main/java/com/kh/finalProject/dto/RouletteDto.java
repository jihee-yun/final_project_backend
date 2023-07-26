package com.kh.finalProject.dto;

import com.kh.finalProject.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RouletteDto {
    private Long id;
    private Date date;
    private Long memberNum;
}
