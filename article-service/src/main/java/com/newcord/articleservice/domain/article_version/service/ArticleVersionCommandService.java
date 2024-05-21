package com.newcord.articleservice.domain.article_version.service;

import com.newcord.articleservice.domain.article_version.entity.ArticleVersion;
import com.newcord.articleservice.domain.article_version.entity.VersionOperation;

public interface ArticleVersionCommandService {
    VersionOperation applyOperation(VersionOperation operation, ArticleVersion articleVersion, long operationIndex);
    ArticleVersion createArticleVersion(Long articleId);
    String getLatestVersion(Long articleId);
}