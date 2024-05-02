package com.newcord.articleservice.domain.block.entity;

import jakarta.persistence.Entity;
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
        private String id;
        private BlockParent parent;
        private boolean has_children;
        private String blockType;
        private String content;
        private BlockCreatedBy created_by;
        private BlockUpdatedBy updated_by;
}
