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
        private String body;
        private NoticeType type;
    }
}
