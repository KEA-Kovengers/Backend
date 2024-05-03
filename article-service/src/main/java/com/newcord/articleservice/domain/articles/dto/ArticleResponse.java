package com.newcord.articleservice.domain.articles.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

// Article 관련 응답 DTO (For MongoDB)
public class ArticleResponse {

    @Builder
    @Getter
    public static class ArticleCreateResponseDTO {
        private Long articleId;
        private List<Long> blockList;
    }

    @Builder
    @Getter
    public static class BlockSequenceUpdateResponseDTO {
        private Long articleId;
        private Long blockId;
        private Long newPosition;
        private List<Long> blockList;
    }

}
