package com.newcord.articleservice.domain.article_version.service;

import com.newcord.articleservice.domain.article_version.entity.ArticleVersion;
import com.newcord.articleservice.domain.article_version.entity.VersionOperation;

public interface ArticleVersionComposeService {
    VersionOperation applyOperation(VersionOperation operation, String version, Long articleId);

}
