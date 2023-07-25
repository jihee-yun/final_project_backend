package com.kh.finalProject.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeEvent {
    private Long memNum;
    private Long reviewId;
    private String memberId;

}
