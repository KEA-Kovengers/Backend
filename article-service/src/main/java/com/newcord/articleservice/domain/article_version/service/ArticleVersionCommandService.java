package com.newcord.articleservice.domain.article_version.service;

import com.newcord.articleservice.domain.article_version.entity.ArticleVersion;
import com.newcord.articleservice.domain.article_version.entity.Version;
import com.newcord.articleservice.domain.article_version.entity.VersionOperation;
import java.time.LocalDateTime;

public interface ArticleVersionCommandService {
    VersionOperation applyOperation(VersionOperation operation, String version, Long articleId);
    ArticleVersion createArticleVersion(Long articleId);

    Version updateVersion(Long articleID);

    String getLatestVersion(Long articleId);
}