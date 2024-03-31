package com.newcord.articleservice.articles;

import com.newcord.articleservice.block.Block;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;



@Builder
public class Articles {
    private String id;
    private List<Block> block_list;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
