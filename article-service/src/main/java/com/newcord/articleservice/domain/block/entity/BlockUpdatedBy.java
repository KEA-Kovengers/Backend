package com.newcord.articleservice.domain.block.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

@Builder
@AllArgsConstructor
@Getter
public class BlockUpdatedBy implements Serializable {
    private Long updater_id;
    @LastModifiedDate
    private LocalDateTime updated_at;

}
