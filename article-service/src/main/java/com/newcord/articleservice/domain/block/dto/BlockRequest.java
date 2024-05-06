package com.newcord.articleservice.domain.block.dto;

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
        private String blockId;               // 수정할 block ID
        private String blockType;           // block 타입
        private Long position;              // block의 위치 (변경되지 않는다면 원래 값 그대로)
        private String content;             // 내용
        private BlockUpdatedBy updated_by;  // 수정자
    }
}
