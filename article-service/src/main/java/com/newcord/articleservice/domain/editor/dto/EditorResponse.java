package com.newcord.articleservice.domain.editor.dto;

import java.util.List;
import java.util.Objects;

import com.newcord.articleservice.domain.editor.entity.Editor;
import com.newcord.articleservice.domain.posts.entity.Posts;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;

// 게시글 편집자 관련 API 응답 DTO
public class EditorResponse {

    // 게시글 편집자 추가 응답 DTO
    @Builder
    @Getter
    public static class EditorAddResponseDTO {
        private Long postId;
        private Long userID;
    }

    @Builder
    @Getter
    public static class DeleteEditorResponseDTO {
        private Long postId;
        private Long userID;
        private boolean postDelete;         //게시글 완전 삭제 여부 (편집자가 없을때 삭제됨)

        public void setPostDelete(boolean postDelete) {
            this.postDelete = postDelete;
        }
    }

    @Builder
    @Getter
    public static class EditorListResponseDTO {
        private Long postId;
        private List<Long> userID;
    }

    @Builder
    @Getter
    @Setter
    public static class EditorPostResponseDTO {
        private Editor postList;
        private int likeCnt;
        private int commentCnt;
    }
    @Builder
    @Getter
    public static class EditorPostListResponseDTO {
        private List<EditorPostResponseDTO> editorPostResponseDTOS;
    }



}
