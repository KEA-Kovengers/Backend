package com.newcord.articleservice.domain.articles.dto;

import com.newcord.articleservice.domain.block.entity.Block;
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
        private List<String> blockList;
        private List<Long> position;
    }

}
