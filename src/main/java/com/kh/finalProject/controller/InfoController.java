package com.kh.finalProject.controller;

import com.kh.finalProject.dto.MemberDto;
import com.kh.finalProject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/info")
@CrossOrigin(origins = "http://localhost:3000")
public class InfoController {
    private final MemberService memberService;

    @GetMapping("/userinfo")
    public ResponseEntity<List<MemberDto>> userInfoByNum(@RequestParam Long usernum) {
        List<MemberDto> list = memberService.getMemberInfoByNum(usernum);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
