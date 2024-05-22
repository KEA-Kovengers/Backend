package com.newcord.articleservice.domain.likes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LikeRequest {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LikeRequestDTO{
        private Long id;
        private Long post_id;
        private Long user_id;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateLikeRequestDTO{

        private Long post_id;
        private Long user_id;
    }

}
