package com.newcord.articleservice.domain.articles.dto;

import com.newcord.articleservice.domain.article_version.entity.OperationType;
import com.newcord.articleservice.domain.block.entity.Block;
import com.newcord.articleservice.domain.block.entity.BlockUpdatedBy;
import java.util.List;
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
        private List<String> blockList;
        private BlockUpdatedBy updated_by;      // 수정자
    }

}
