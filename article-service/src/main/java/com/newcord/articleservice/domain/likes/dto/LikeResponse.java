package com.newcord.articleservice.domain.likes.dto;

import com.newcord.articleservice.domain.likes.entity.Likes;
import com.newcord.articleservice.domain.posts.entity.Posts;
import lombok.*;

public class LikeResponse {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LikeResponseDTO{
        Likes likes;
        Posts posts;
        private int likeCnt;
        private int commentCnt;
    }
}
