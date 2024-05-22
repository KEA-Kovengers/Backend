package com.newcord.articleservice.domain.articles.dto;

import com.newcord.articleservice.domain.article_version.entity.OperationEntityType;
import com.newcord.articleservice.domain.article_version.entity.OperationType;
import com.newcord.articleservice.domain.block.entity.Block;
import com.newcord.articleservice.domain.block.entity.BlockUpdatedBy;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

// Article 관련 요청 DTO (For MongoDB)
public class ArticleRequest {
    @Builder
    @Getter
    public static class InsertBlockRequestDTO {
        private Long position;
        private Block block;
    }

    @Builder
    @Getter
    public static class BlockSequenceUpdateRequestDTO {
        private String blockID;
        private Long position;
        private String articleVersion; // {version index}.{operation index} 형태
        private OperationType operationType;    // Operation Type (TEXT_INSERT, TEXT_DELETE, TAG)
        private OperationEntityType entityType;  // Operation Entity Type
        private BlockUpdatedBy updated_by;      // 수정자
    }

}
