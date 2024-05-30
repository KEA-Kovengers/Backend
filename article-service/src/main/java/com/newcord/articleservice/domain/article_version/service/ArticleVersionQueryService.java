package com.newcord.articleservice.domain.article_version.service;

import com.newcord.articleservice.domain.article_version.entity.ArticleVersion;

public interface ArticleVersionQueryService {

    ArticleVersion findArticleVersionById(Long articleId);
}
