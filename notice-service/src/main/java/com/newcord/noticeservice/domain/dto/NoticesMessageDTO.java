package com.newcord.noticeservice.domain.dto;

import com.newcord.noticeservice.domain.entity.NoticeType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class NoticesMessageDTO {
    private Long user_id;
    private String body;
    private NoticeType type;
}
