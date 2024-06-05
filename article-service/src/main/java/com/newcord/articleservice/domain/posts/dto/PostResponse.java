package com.newcord.articleservice.domain.posts.dto;

import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockDTO;
import com.newcord.articleservice.domain.editor.entity.Editor;
import com.newcord.articleservice.domain.posts.entity.Thumbnail;
import com.newcord.articleservice.domain.posts.enums.PostStatus;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

//게시글 관련 API 응답 DTO
public class PostResponse {

    // 게시글 생성 응답 DTO
    @Builder
    @Getter
    public static class PostCreateResponseDTO{
        private Long id;
    }

@Builder
@Getter
@Setter
public static class PostResponseDTO{
        private Long id;
        private Long views;
        private String title;
        private String body;
        private List<Thumbnail> thumbnails;
    private PostStatus status;
    private LocalDateTime created;
    private LocalDateTime updated;
    private int likeCnt;
    private int commentCnt;

}

    @Builder
    @Getter
    @Setter
    public static class SocialPostResponseDTO{
        private Long id;
        private Long views;
        private String title;
        private String body;
        private List<Thumbnail> thumbnails;
        private PostStatus status;
        private LocalDateTime created;
        private LocalDateTime updated;
        private int likeCnt;
        private int commentCnt;
        private List<Long> userId;

    }
    @Builder
    @Getter
    public static class PostDetailResponseDTO{
        private Long id;
        private List<Thumbnail> thumbnails;
        private String title;
        private String articleVersion;
        private String body;
        private PostStatus status;
        private Long views;
        private List<String> blockSequence;
        private List<BlockDTO> blockList;
        private List<String> hashtags;
        private LocalDateTime created;
        private LocalDateTime updated;
    }

    @Builder
    @Getter
    @Setter
    public static class PostListResponseDTO{
        private Page<Editor> postList;
    }

    @Builder
    @Getter
    public static class SocialPostListDTO{
        private Page<SocialPostResponseDTO> postsList;
    }
}
