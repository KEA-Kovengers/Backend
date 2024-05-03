package com.newcord.articleservice.domain.block.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Getter
public class Block implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private BlockParent parent;
        private boolean has_children;
        private String blockType;
        private String content;
        private BlockCreatedBy created_by;
        private BlockUpdatedBy updated_by;

        public void updateContent(String content, BlockUpdatedBy updated_by) {
            this.content = content;
            this.updated_by = updated_by;
        }

        public void updateBlockType(String blockType, BlockUpdatedBy updated_by) {
            this.blockType = blockType;
            this.updated_by = updated_by;
        }
}
