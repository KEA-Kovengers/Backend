package com.newcord.articleservice.domain.editor.dto;

import com.newcord.articleservice.domain.editor.entity.Editor;
import lombok.Builder;
import lombok.Getter;

// 게시글 편집자관련 API 요청 DTO
public class EditorRequest {

    // 게시글 편집자 추가 요청 DTO
    @Builder
    @Getter
    public static class EditorAddRequestDTO{
        private Long postId;
        private String userID;
    }

}
