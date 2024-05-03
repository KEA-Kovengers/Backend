package com.newcord.articleservice.domain.posts.dto;

import lombok.Builder;
import lombok.Getter;

//게시글 관련 API 응답 DTO
public class PostResponse {

    // 게시글 생성 응답 DTO
    @Builder
    @Getter
    public static class PostCreateResponseDTO{
        private Long id;
    }

}
