package com.kh.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GuildDetailDto {
    private Long id;
    private String guildName;
    private String thumbnail;
    private String region;
    private int limitMember;
    private String meetDay;
    private String detailIntro;
    private String leaderId;
    private String leaderIntro;

    private List<String> leaderProfileList; // 길드장 프로필 리스트
    private List<String> memberProfileList; // 길드원 프로필 리스트
}
