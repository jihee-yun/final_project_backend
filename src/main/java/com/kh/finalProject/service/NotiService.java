package com.kh.finalProject.service;

import com.kh.finalProject.entity.Member;
import com.kh.finalProject.entity.Review;
import com.kh.finalProject.notification.LikeEvent;
import com.kh.finalProject.repository.MemberRepository;
import com.kh.finalProject.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotiService {
    private final SimpMessagingTemplate messagingTemplate;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    public void sendNotification(Long memNum, Long reviewId) {
        Optional<Member> member = memberRepository.findByMemberNum(memNum);
        Optional<Review> review = reviewRepository.findById(reviewId);
//        LikeEvent likeEvent = new LikeEvent();
        Long receiveMem = null;
        String sendId = null;
        if(member.isPresent() && review.isPresent()) {
            receiveMem = review.get().getUserNum();
            sendId = member.get().getMemberId();
//            likeEvent.setMemberId(member.get().getMemberId());
        }
//        Map<String, Object> notification = new HashMap<>();
//        notification.put("message", likeEvent.getMemberId() + "님이 당신의 리뷰에 좋아요를 눌렀습니다");
//        notification.put("time", new Date().toString());
        messagingTemplate.convertAndSend("/sub/"+ receiveMem, sendId + "님이 당신의 리뷰에 좋아요를 눌렀습니다");
    }
}
