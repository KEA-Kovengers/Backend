package com.newcord.articleservice.domain.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentsResponse {


    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentsResponseDTO{
        private Long id;
        private Long commentID;
        private Long userID;
        private String body;
    }
}
