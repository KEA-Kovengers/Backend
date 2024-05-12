package com.newcord.articleservice.domain.articles.service;

import com.newcord.articleservice.domain.articles.entity.Article;
import java.util.List;

public interface ArticlesQueryService {
    Article findArticleById(Long articleId);            // Article 조회

}
