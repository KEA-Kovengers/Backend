package com.newcord.articleservice.domain.articles.entity;

import com.newcord.articleservice.domain.block.entity.Block;
import com.newcord.articleservice.global.common.BaseTimeEntity;
import java.util.List;
import lombok.Builder;



@Builder
public class Article extends BaseTimeEntity {
    private String id;
    private List<Block> block_list;
}
