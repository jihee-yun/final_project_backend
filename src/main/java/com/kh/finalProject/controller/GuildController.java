package com.kh.finalProject.controller;

import com.kh.finalProject.dto.GuildDetailDto;
import com.kh.finalProject.dto.GuildDto;
import com.kh.finalProject.service.GuildService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/guild")
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
public class GuildController {
    private final GuildService guildService;

    // 생성 길드 전체 조회
    @GetMapping("/all")
    public ResponseEntity<List<GuildDto>> allGuildList(@RequestParam String guildList) {
        System.out.println("넘어온 값 : " + guildList);
        List<GuildDto> list = guildService.allGuildListWithUsers(guildList);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 길드 상세 정보 조회
    @GetMapping("/detail")
    public ResponseEntity<List<GuildDetailDto>> guildDetail(@RequestParam Long guildNum) {
        System.out.println("넘어온 값 : " + guildNum);
        List<GuildDetailDto> list = guildService.guildDetail(guildNum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 새로운 길드 생성
    @PostMapping("/newGuild")
    public ResponseEntity<Boolean> createNewGuild(@RequestBody Map<String, String> guildData) {
        Long num = Long.valueOf(guildData.get("memNum"));
        String name = guildData.get("guildName");
        String intro = guildData.get("guildIntro");
        String detailIntro = guildData.get("guildDetailIntro");
        String meetDayStr = guildData.get("meetDay") + " 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime meetDay = LocalDateTime.parse(meetDayStr, formatter);
        int category = Integer.parseInt(guildData.get("category"));
        int limitMem = Integer.parseInt(guildData.get("member"));
        String region = guildData.get("region");
        String thumbnail = guildData.get("thumbnail");
        boolean result = guildService.createNewGuild(num, category, name, intro, detailIntro, meetDay, limitMem, region, thumbnail);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 길드 가입 회원 확인
    @GetMapping("/isMember")
    public ResponseEntity<Integer> isMember(@RequestParam Long guildNum, Long userNum) {
        System.out.println("유저 번호 : " + userNum);
        System.out.println("길드 번호 : " + guildNum);
        int isMember = guildService.isMember(guildNum, userNum);
        return new ResponseEntity<>(isMember, HttpStatus.OK);
    }

    // 길드 가입하기
    @PostMapping("/join")
    public ResponseEntity<Boolean> joinGuild(@RequestBody Map<String, Long> joinData) {
        Long guildNum = joinData.get("guildNum");
        Long userNum = joinData.get("userNum");
        boolean result = guildService.joinGuild(guildNum, userNum);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 회원 번호로 가입한 길드 정보 조회
    // 회원정보 수정에 필요한 회원 정보 대부분 조회
    @GetMapping("/guildinfo")
    public ResponseEntity<List<GuildDto>> guildInfoByMemberNum(@RequestParam Long membernum) {
        List<GuildDto> list = guildService.getGuildInfoByMemberNum(membernum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
