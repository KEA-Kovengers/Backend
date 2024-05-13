package com.newcord.articleservice.domain.posts.dto;

import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockDTO;
import com.newcord.articleservice.domain.editor.entity.Editor;
import com.newcord.articleservice.domain.hashtags.entity.Hashtags;
import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.domain.posts.enums.PostStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
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
    public static class PostDetailResponseDTO{
        private Long id;
        private String thumbnail;
        private String title;
        private String body;
        private PostStatus status;
        private Long views;
        private List<String> blockSequence;
        private List<BlockDTO> blockList;
        private List<String> hashtags;
    }

    @Builder
    @Getter
    public static class PostListResponseDTO{
        private Page<Editor> postList;
    }
}
