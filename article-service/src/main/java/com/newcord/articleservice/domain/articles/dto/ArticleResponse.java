package com.newcord.articleservice.domain.articles.dto;

import com.newcord.articleservice.domain.article_version.entity.OperationEntityType;
import com.newcord.articleservice.domain.article_version.entity.OperationType;
import com.newcord.articleservice.domain.block.entity.Block;
import com.newcord.articleservice.domain.block.entity.BlockUpdatedBy;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

// Article 관련 응답 DTO (For MongoDB)
public class ArticleResponse {

    @Builder
    @Getter
    public static class ArticleCreateResponseDTO {
        private Long articleId;
        private List<String> blockList;
    }

    @Builder
    @Getter
    public static class BlockSequenceUpdateResponseDTO {
        private Long articleId;
        private String blockID;
        private Long position;
        private String articleVersion; // {version index}.{operation index} 형태
        private OperationType operationType;    // Operation Type (TEXT_INSERT, TEXT_DELETE, TAG)
        private OperationEntityType entityType;  // Operation Entity Type
        private List<String> blockList;
        private BlockUpdatedBy updated_by;      // 수정자
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class TitleUpdateResponseDTO {
        private Long articleId;
        private String articleVersion; // {version index}.{operation index} 형태
        private OperationType operationType;    // Operation Type (TEXT_INSERT, TEXT_DELETE, TAG)
        private OperationEntityType entityType;  // Operation Entity Type
        private Long position;                  // 블럭 내에서 위치
        private String content;                 // 내용               OperationType이 TEXT_INSERT, TEXT_DELETE인 경우 필수
        private BlockUpdatedBy updated_by;      // 수정자
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class HashtagUpdateResponseDTO {
        private Long articleID;
        private String articleVersion;
        private OperationType operationType;
        private OperationEntityType entityType;
        private String tagName;
        private BlockUpdatedBy updated_by;
    }

}
