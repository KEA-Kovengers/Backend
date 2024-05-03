package com.newcord.articleservice.domain.articles.entity;

import com.newcord.articleservice.global.common.BaseMongoTimeEntity;
import java.util.List;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
public class Article extends BaseMongoTimeEntity {
    @Id
    private Long id;
    private List<Long> block_list;
}
