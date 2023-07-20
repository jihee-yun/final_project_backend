package com.kh.finalProject.dto;

import com.kh.finalProject.entity.Cafe;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class MainDto {
    private Long id;
    private String cafeName;
    private String intro;
    private String thumbnail;

}
