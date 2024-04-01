package com.newcord.articleservice.block.entity;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public class BlockUpdatedBy {
    private String updater_id;
    private LocalDateTime updated_at;

}
