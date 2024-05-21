package com.newcord.articleservice.domain.article_version.service;

import com.newcord.articleservice.domain.article_version.entity.ArticleVersion;
import com.newcord.articleservice.domain.article_version.entity.VersionOperation;
import com.newcord.articleservice.domain.article_version.repository.ArticleVersionRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleVersionComposeServiceImpl implements ArticleVersionComposeService{
    private final ArticleVersionCommandService articleVersionCommandService;
    private final ArticleVersionRepository articleVersionRepository;

    @Override
    public VersionOperation applyOperation(VersionOperation operation, String version,
        Long articleId) {
        ArticleVersion articleVersion = articleVersionRepository.findById(articleId)
            .orElseThrow(() -> new ApiException(ErrorStatus._ARTICLE_NOT_FOUND));

        String[] versionArr = version.split("\\.");
        VersionOperation appliedOperation = articleVersionCommandService.applyOperation(operation, articleVersion,Long.parseLong(versionArr[1]));

        articleVersion.getVersions().get(articleVersion.getVersions().size() - 1).getOperations().add(appliedOperation);
        articleVersionRepository.save(articleVersion);

        return appliedOperation;
    }
}
