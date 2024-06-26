package com.newcord.articleservice.domain.articles.entity;

import com.newcord.articleservice.global.common.BaseMongoTimeEntity;
import jakarta.persistence.GeneratedValue;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Getter
public class Article extends BaseMongoTimeEntity {
    @Id
    private Long id;
    private List<String> block_list;
}
