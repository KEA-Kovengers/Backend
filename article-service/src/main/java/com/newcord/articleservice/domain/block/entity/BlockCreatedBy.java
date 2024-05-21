package com.newcord.articleservice.domain.block.entity;


import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

@Builder
@Getter
public class BlockCreatedBy implements Serializable {
    private Long creator_id;
    @CreatedDate
    private LocalDateTime created_at;
}
