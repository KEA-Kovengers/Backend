package com.newcord.articleservice.domain.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentsRequest {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentsCreateRequestDTO{
        private Long postID;
        private Long userID;
        private Long commentID;
       private String body;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentsRequestDTO{
        private Long id;
    }
}
