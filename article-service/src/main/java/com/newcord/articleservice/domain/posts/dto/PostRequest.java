package com.newcord.articleservice.domain.posts.dto;

import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.domain.posts.enums.PostStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

// 게시글 관련 API 요청 DTO
public class PostRequest {

    // 게시글 생성 요청 DTO
    @Builder
    @Getter
    public static class PostCreateRequestDTO {
        private String thumbnail;
        private String title;
        private String body;
        private PostStatus status;

        public Posts toEntity(PostCreateRequestDTO dto){
            return Posts.builder()
                    .thumbnail(dto.getThumbnail())
                    .title(dto.getTitle())
                    .body(dto.getBody())
                    .status(dto.getStatus())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class PostUpdateRequestDTO{
        private Long id;
        private String thumbnail;
        private String title;
        @Enumerated(EnumType.ORDINAL)
        private PostStatus status;
    }

}
