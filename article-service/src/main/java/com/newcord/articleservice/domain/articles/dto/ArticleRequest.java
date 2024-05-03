package com.newcord.articleservice.domain.articles.dto;

import lombok.Builder;
import lombok.Getter;

// Article 관련 요청 DTO (For MongoDB)
public class ArticleRequest {

    @Builder
    @Getter
    public static class ArticleCreateRequestDTO {
        private Long articleId;
    }

    @Builder
    @Getter
    public static class BlockSequenceUpdateRequestDTO {
        private Long articleId;
        private Long blockId;
        private Long position;
    }

}
