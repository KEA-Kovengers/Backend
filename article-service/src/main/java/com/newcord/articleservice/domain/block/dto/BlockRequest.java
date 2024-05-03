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
    public static class BlockCreateDTO {
        private String blockType;           // 블럭타입
        private Long position;              // 블럭이 추가될 위치 (0부터 시작)
        private String content;             // 내용
        private BlockParent blockParent;    // 부모 블럭 (없는경우 article ID)
        private BlockCreatedBy created_by;  // 생성자
    }

    // 블럭 내용 수정 요청 DTO
    @Builder
    @AllArgsConstructor
    @Getter
    public static class BlockContentUpdateDTO {
        private Long blockId;
        private String blockType;
        private String content;
        private BlockUpdatedBy updated_by;
    }
}
