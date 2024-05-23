package com.newcord.articleservice.domain.article_version.entity;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Version {

    private LocalDateTime timestamp;           // 첫 Operation 시간 (버전을 나누기 위함)
    private List<VersionOperation> operations;
}
