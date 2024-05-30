package com.newcord.articleservice.domain.log.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

public class LogResponse {
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

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EditorLogListResponseDTO{
        private List<EditorLogResponseDTO> editorLogResponseDTOS;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BlockLogDataResponseDTO{
        private String blockId;
        private Long creatorId;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BlockLogListDataResponseDTO{
        private List<BlockLogDataResponseDTO> blockLogDataResponseDTOList;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BlockCreatorDataResponseDTO {
        private Long creatorId;
        private List<String> blockId;
    }
}
