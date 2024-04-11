package com.newcord.articleservice.domain.block.entity;


import java.time.LocalDateTime;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;

@Builder
public class BlockCreatedBy {
    private String creator_id;
    @CreatedDate
    private LocalDateTime created_at;
}
