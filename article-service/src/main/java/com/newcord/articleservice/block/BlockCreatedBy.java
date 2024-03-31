package com.newcord.articleservice.block;


import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public class BlockCreatedBy {
    private String creator_id;
    private LocalDateTime created_at;
}
