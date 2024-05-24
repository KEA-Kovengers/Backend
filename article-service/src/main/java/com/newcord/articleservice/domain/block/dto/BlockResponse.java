package com.newcord.articleservice.domain.block.dto;

import com.newcord.articleservice.domain.article_version.entity.OperationEntityType;
import com.newcord.articleservice.domain.article_version.entity.OperationType;
import com.newcord.articleservice.domain.block.entity.Block;
import com.newcord.articleservice.domain.block.entity.BlockCreatedBy;
import com.newcord.articleservice.domain.block.entity.BlockParent;
import com.newcord.articleservice.domain.block.entity.BlockUpdatedBy;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class BlockResponse {

    // Block객체의 id가 ObjectId로 되어있어서 String으로 변환해서 사용
    @Builder
    @AllArgsConstructor
    @Getter
    public static class BlockDTO{
        private String id;
        private BlockParent parent;
        private boolean has_children;
        private String content;
        private BlockCreatedBy created_by;
        private BlockUpdatedBy updated_by;

        public static BlockDTO toDTO(Block block){
            return BlockDTO.builder()
                .id(block.getId().toString())
                .parent(block.getParent())
                .has_children(block.isHas_children())
                .content(block.getContent())
                .created_by(block.getCreated_by())
                .updated_by(block.getUpdated_by())
                .build();
        }
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class BlockCreateResponseDTO{
        private Long articleId;
        private String articleVersion;
        private OperationType operationType;
        private OperationEntityType entityType;  // Operation Entity Type
        private BlockDTO blockDTO;
        private Long position;
        private List<String> blockList;
    }

    // 블럭 내용 수정 요청 DTO
    @Builder
    @AllArgsConstructor
    @Getter
    public static class BlockContentUpdateResponseDTO {
        private String blockId;                 // 수정할 block ID
        private String articleVersion; // {version index}.{operation index} 형태
        private OperationType operationType;    // Operation Type (TEXT_INSERT, TEXT_DELETE, TAG)
        private OperationEntityType entityType;  // Operation Entity Type
        private Long position;                  // 블럭 내에서 위치
        private String content;                 // 내용               OperationType이 TEXT_INSERT, TEXT_DELETE인 경우 필수
        private BlockUpdatedBy updated_by;  // 수정자
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class BlockDeleteResponseDTO {
        private String blockId;
        private List<String> blockList;
    }

    @Builder
    @Getter
    public static class BlockLogDataResponseDTO{
        private String blockId;
        private Long creatorId;
    }
}
