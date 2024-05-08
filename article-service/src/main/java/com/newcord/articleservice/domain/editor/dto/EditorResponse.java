package com.newcord.articleservice.domain.editor.dto;

import lombok.Builder;
import lombok.Getter;

// 게시글 편집자 관련 API 응답 DTO
public class EditorResponse {

    // 게시글 편집자 추가 응답 DTO
    @Builder
    @Getter
    public static class EditorAddResponseDTO{
        private Long postId;
        private String userID;
    }

    @Builder
    @Getter
    public static class DeleteEditorResponseDTO{
        private Long postId;
        private String userID;
        private boolean postDelete;         //게시글 완전 삭제 여부 (편집자가 없을때 삭제됨)

        public void setPostDelete(boolean postDelete){
            this.postDelete = postDelete;
        }
    }

}
