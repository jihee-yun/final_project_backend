package com.kh.finalProject.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.finalProject.service.NotiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final NotiService notiService;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("WebSocket 메시지를 받았습니다. Session ID: {}, Payload: {}", session.getId(), payload);

        // 클라이언트로부터 받은 메시지를 객체로 변환
        LikeEvent likeEvent = objectMapper.readValue(payload, LikeEvent.class);

        // 좋아요 이벤트 처리 로직을 호출
        // 여기서는 실시간 알림을 전송하는 로직이 이미 NotiService 클래스에 구현되어 있기 때문에 NotiService의 메서드만 호출하면 됩니다.
        notiService.sendNotification(likeEvent.getMemNum(), likeEvent.getReviewId());
    }
}
