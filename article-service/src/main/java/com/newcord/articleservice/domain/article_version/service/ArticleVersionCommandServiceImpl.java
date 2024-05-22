package com.newcord.articleservice.domain.article_version.service;

import com.newcord.articleservice.domain.article_version.entity.ArticleVersion;
import com.newcord.articleservice.domain.article_version.entity.Version;
import com.newcord.articleservice.domain.article_version.entity.VersionOperation;
import com.newcord.articleservice.domain.article_version.repository.ArticleVersionRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleVersionCommandServiceImpl implements ArticleVersionCommandService{
    private final ArticleVersionRepository articleVersionRepository;
    private final Object lock = new Object();


    @Override
    @Transactional
    public VersionOperation applyOperation(VersionOperation operation, String version,
        Long articleId) {
        ArticleVersion articleVersion = articleVersionRepository.findById(articleId)
            .orElseThrow(() -> new ApiException(ErrorStatus._ARTICLE_NOT_FOUND));

        String[] versionArr = version.split("\\.");
//        VersionOperation appliedOperation = this.applyOperation(operation, articleVersion,Long.parseLong(versionArr[1]));
        VersionOperation appliedOperation = operation;

        synchronized (lock) {
            //버전상 충돌하는 경우 처리

            // operation의 position 수정하고 DB에 저장
        }

        articleVersion.getVersions().get(articleVersion.getVersions().size() - 1).getOperations().add(appliedOperation);
        articleVersionRepository.save(articleVersion);

        return appliedOperation;
    }

    @Override
    public ArticleVersion createArticleVersion(Long articleId) {
        ArticleVersion articleVersion = ArticleVersion.builder()
            .id(articleId)
            .versions(new ArrayList<>())
            .build();

        Version version = Version.builder()
            .timestamp(LocalDateTime.now())
            .operations(new ArrayList<>())
            .build();

        articleVersion.getVersions().add(version);

        articleVersionRepository.save(articleVersion);

        return articleVersion;
    }

    @Override
    public String getLatestVersion(Long articleID) {
        ArticleVersion articleVersion = articleVersionRepository.findById(articleID)
            .orElseThrow(() -> new ApiException(ErrorStatus._ARTICLE_NOT_FOUND));

        List<Version> versions = articleVersion.getVersions();
        int lastVersionIdx = versions.size() - 1;
        List<VersionOperation> operations = versions.get(lastVersionIdx).getOperations();
        int lastOperation = operations.size() - 1;

        return lastVersionIdx + "." + lastOperation;
    }
}
