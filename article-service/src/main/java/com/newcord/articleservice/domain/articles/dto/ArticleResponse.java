package com.newcord.articleservice.domain.articles.dto;

import com.newcord.articleservice.domain.block.entity.Block;
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
        private Block block;
        private Long newPosition;
        private List<String> blockList;
    }

}
