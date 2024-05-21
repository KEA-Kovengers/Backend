package com.newcord.articleservice.domain.block.dto;

import com.newcord.articleservice.domain.article_version.entity.OperationType;
import com.newcord.articleservice.domain.block.entity.BlockCreatedBy;
import com.newcord.articleservice.domain.block.entity.BlockParent;
import com.newcord.articleservice.domain.block.entity.BlockUpdatedBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


// 블럭 관련 API 요청 DTO
public class BlockRequest {

    // 블럭 생성 요청 DTO
    @Builder
    @AllArgsConstructor
    @Getter
    public static class BlockCreateRequestDTO {
        private Long articleID;
        private final String articleVersion; // {version index}.{operation index} 형태
        private String blockType;           // 블럭타입
        private Long position;              // 블럭이 추가될 위치 (0부터 시작)
        private String content;             // 내용
        private BlockParent blockParent;    // 부모 블럭 (없는경우 article ID)
        private BlockCreatedBy created_by;  // 생성자

        public void setArticleID(Long articleID) {
            this.articleID = articleID;
        }
    }

    // 블럭 내용 수정 요청 DTO
    @Builder
    @AllArgsConstructor
    @Getter
    public static class BlockContentUpdateRequestDTO {
        private String blockId;                 // 수정할 block ID
        private String articleVersion; // {version index}.{operation index} 형태
        private OperationType operationType;    // Operation Type (TEXT_INSERT, TEXT_DELETE, TAG)
        private Long position;                  // 블럭 내에서 위치
        private String content;                 // 내용               OperationType이 TEXT_INSERT, TEXT_DELETE인 경우 필수
        private BlockUpdatedBy updated_by;      // 수정자

        public void setPosition(Long position) {
            this.position = position;
        }
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class BlockDeleteRequestDTO {
        private String blockId;
    }
}
