package com.newcord.articleservice.domain.block.entity;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BlockParent implements Serializable {
    private String type;
    private String page_id;
}
