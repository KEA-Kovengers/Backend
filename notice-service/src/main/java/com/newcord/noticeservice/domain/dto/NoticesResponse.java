package com.newcord.noticeservice.domain.dto;

import com.newcord.noticeservice.domain.entity.NoticeType;
import com.newcord.noticeservice.domain.entity.Notices;
import lombok.*;

import java.time.LocalDateTime;

public class NoticesResponse {

    @Builder
    @Getter
    @Setter
    public static class NoticesResponseDTO {
        private Long user_id;
        private String body;
        private NoticeType type;
        private LocalDateTime created_at;

        public static NoticesResponseDTO createResponse(Notices notices) {
            return NoticesResponseDTO.builder()
                    .user_id(notices.getUser_id())
                    .body(notices.getBody())
                    .type(notices.getType())
                    .created_at(notices.getCreated_at())
                    .build();
        }
    }
}
