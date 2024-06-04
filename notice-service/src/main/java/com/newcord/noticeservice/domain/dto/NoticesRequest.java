package com.newcord.noticeservice.domain.dto;

import com.newcord.noticeservice.domain.entity.NoticeType;
import com.newcord.noticeservice.domain.entity.Notices;
import lombok.*;

import java.time.LocalDateTime;

public class NoticesRequest {

    @Builder
    @Getter
    @Setter
    public static class NoticesRequestDTO {
        private Long user_id;
        private Long from_id;
        private Long post_id;
        private Long comment_id;
        private NoticeType type;
    }
}
