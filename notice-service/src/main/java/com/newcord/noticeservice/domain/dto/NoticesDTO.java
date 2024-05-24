package com.newcord.noticeservice.domain.dto;

import com.newcord.noticeservice.domain.entity.NoticeType;
import com.newcord.noticeservice.domain.entity.Notices;
import com.newcord.noticeservice.domain.entity.StatusType;
import jakarta.persistence.Id;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

public class NoticesDTO {

    @AllArgsConstructor
    @NoArgsConstructor
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
