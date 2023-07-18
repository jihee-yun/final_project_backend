package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GuildDto {
    private Long id;
    private String guildName;
    private String thumbnail;
    private String region;
    private int limitMember;
    private int category;
    private int countMember;

    private List<String> memberProfileList; // 길드원 프로필 리스트
}
