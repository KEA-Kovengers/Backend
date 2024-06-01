package com.newcord.articleservice.domain.posts.dto;

import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.domain.posts.entity.Thumbnail;
import com.newcord.articleservice.domain.posts.enums.PostStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

// 게시글 관련 API 요청 DTO
public class PostRequest {

    // 게시글 생성 요청 DTO
    @Builder
    @Getter
    public static class PostCreateRequestDTO {
        private List<Thumbnail> thumbnails;
        private String title;
        private String body;
        private List<String> hashtags;


        public Posts toEntity(PostCreateRequestDTO dto){
            return Posts.builder()
                    .thumbnails(dto.getThumbnails())
                    .title(dto.getTitle())
                    .body(dto.getBody())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class PostUpdateRequestDTO{
        private Long id;
        private List<Thumbnail> thumbnails;
        private String title;
        @Enumerated(EnumType.ORDINAL)
        private PostStatus status;
    }

    @Builder
    @Getter
    public static class PostUpdateHashtagsRequestDTO{
        private Long postId;
        private List<String> hashtags;
    }

}
