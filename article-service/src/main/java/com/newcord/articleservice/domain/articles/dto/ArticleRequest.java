package com.newcord.articleservice.domain.articles.dto;

import com.newcord.articleservice.domain.block.entity.Block;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

// Article 관련 요청 DTO (For MongoDB)
public class ArticleRequest {

    @Builder
    @Getter
    public static class ArticleCreateRequestDTO {
        private Long articleId;
    }

    @Builder
    @Getter
    public static class InsertBlockRequestDTO {
        private Long articleId;
        private Long position;
        private Block block;
    }

    @Builder
    @Getter
    public static class BlockSequenceUpdateRequestDTO {
        private Long articleId;
        private Long blockId;
        private Long position;
    }

}
