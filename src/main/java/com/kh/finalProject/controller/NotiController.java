package com.kh.finalProject.controller;

import com.kh.finalProject.service.NotiService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class NotiController {
    private final NotiService notiService;

    @MessageMapping("/notification")
    @SendTo("/topic/notification")
    public void likeNotification(@RequestBody Map<String, Long> likeData) {
        Long reviewId = likeData.get("reviewNum");
        Long memNum = likeData.get("memNum");
        notiService.sendNotification(reviewId, memNum);
    }
}
