package com.newcord.articleservice.domain.articles.dto;

import com.newcord.articleservice.domain.article_version.entity.OperationEntityType;
import com.newcord.articleservice.domain.article_version.entity.OperationType;
import com.newcord.articleservice.domain.block.entity.Block;
import com.newcord.articleservice.domain.block.entity.BlockUpdatedBy;
import java.util.List;
import lombok.AllArgsConstructor;
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

    @Builder
    @AllArgsConstructor
    @Getter
    public static class TitleUpdateRequestDTO {
        private Long articleID;
        private String articleVersion; // {version index}.{operation index} 형태
        private OperationType operationType;    // Operation Type (TEXT_INSERT, TEXT_DELETE, TAG, TITLE, HASHTAG)
        private OperationEntityType entityType;  // Operation Entity Type
        private Long position;                  // 블럭 내에서 위치
        private String content;                 // 내용               OperationType이 TEXT_INSERT, TEXT_DELETE인 경우 필수
        private BlockUpdatedBy updated_by;      // 수정자

        public void setPosition(Long position) {
            this.position = position;
        }
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class HashtagUpdateRequestDTO {
        private Long articleID;
        private String articleVersion;
        private OperationType operationType;
        private OperationEntityType entityType;
        private String tagName;
        private BlockUpdatedBy updated_by;
    }

}
