package com.newcord.articleservice.domain.block.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import org.springframework.data.annotation.LastModifiedDate;

@Builder
public class BlockUpdatedBy {
    private String updater_id;
    @LastModifiedDate
    private LocalDateTime updated_at;

}
