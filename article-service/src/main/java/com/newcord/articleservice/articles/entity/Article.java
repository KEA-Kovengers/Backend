package com.newcord.articleservice.articles.entity;

import com.newcord.articleservice.block.entity.Block;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;



@Builder
public class Article {
    private String id;
    private List<Block> block_list;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
