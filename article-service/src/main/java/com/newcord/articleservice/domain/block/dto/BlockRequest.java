package com.newcord.articleservice.domain.block.dto;

import com.newcord.articleservice.domain.block.entity.BlockCreatedBy;
import com.newcord.articleservice.domain.block.entity.BlockParent;
import com.newcord.articleservice.domain.block.entity.BlockUpdatedBy;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


public class BlockRequest {

    @Builder
    @AllArgsConstructor
    @Getter
    public static class BlockCreateDTO {
        private String blockType;
        private String content;
        private BlockParent blockParent;
        private BlockCreatedBy created_by;
    }
    @Builder
    @AllArgsConstructor
    @Getter
    public static class BlockContentUpdateDTO {
        private String id;
        private String blockType;
        private String content;
        private BlockUpdatedBy updated_by;
    }
}
