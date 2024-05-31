package com.newcord.noticeservice.domain.dto;

import com.newcord.noticeservice.domain.entity.NoticeType;
import com.newcord.noticeservice.domain.entity.StatusType;
import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class NoticesResponse {

    @Builder
    @Getter
    @Setter
    public static class NoticesResponseDTO {
        private String id;
        private Long user_id;
        private String body;
        private NoticeType type;
        private StatusType status;
        private LocalDateTime created_at;
    }
}
