package com.newcord.articleservice.domain.editor.dto;

import java.util.List;
import java.util.Objects;

import lombok.*;
import org.bson.types.ObjectId;

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
    public static class EditorLogResponseDTO {
        private Long userID;
        private String blockId;


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EditorLogResponseDTO that = (EditorLogResponseDTO) o;
            return Objects.equals(userID, that.userID) &&
                    Objects.equals(blockId, that.blockId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userID, blockId);
        }
    }
}
