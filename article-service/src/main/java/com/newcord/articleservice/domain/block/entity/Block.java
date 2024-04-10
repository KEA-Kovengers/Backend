package com.newcord.articleservice.domain.block.entity;

import lombok.Builder;

@Builder
public class Block {
        private String id;
        private BlockParent parent;
        private boolean has_children;
        private String blockType;
        private String content;
        private BlockCreatedBy created_by;
        private BlockUpdatedBy updated_by;
}
