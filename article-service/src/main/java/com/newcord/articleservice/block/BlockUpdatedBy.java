package com.newcord.articleservice.block;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public class BlockUpdatedBy {
    private String updater_id;
    private LocalDateTime updated_at;

}
