package com.kh.finalProject.controller;

import com.kh.finalProject.dto.GuildDto;
import com.kh.finalProject.service.GuildService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/guild")
@CrossOrigin(origins = "http://localhost:3000")
public class GuildController {
    private final GuildService guildService;

    @GetMapping("/all")
    public ResponseEntity<List<GuildDto>> allGuildList(@RequestParam String guildList) {
        System.out.println("넘어온 값 : " + guildList);
        List<GuildDto> list = guildService.allGuildListWithUsers(guildList);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
