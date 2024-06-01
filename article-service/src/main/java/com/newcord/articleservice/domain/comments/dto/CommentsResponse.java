package com.newcord.articleservice.domain.comments.dto;

import com.newcord.articleservice.domain.comments.entity.Comments;
import com.newcord.articleservice.domain.posts.entity.Posts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

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
        private Boolean isDeleted;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentsPostResponseDTO{
        private Long id;
        private Long commentID;
        private Long userID;
        private String body;
        private Boolean isDeleted;
        Posts posts;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentsUserResponseDTO{
        Comments comments;
        Posts posts;
        private int likeCnt;
        private int commentCnt;
    }

}
