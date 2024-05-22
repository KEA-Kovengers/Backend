package com.newcord.articleservice.domain.article_version.entity;

import com.newcord.articleservice.domain.block.entity.BlockUpdatedBy;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

@Builder
@Getter
public class VersionOperation {
    private OperationType operationType;            //Operation Type
    private OperationEntityType entityType;         //Operation Entity Type
    private ObjectId id;                            //수정한 블럭 ID
    private LocalDateTime timestamp;                       //수정 요청한 시간(클라이언트 시간)
    private long position;                          //블럭 내에서 위치
    private String content;
    private BlockUpdatedBy updated_by;
}
