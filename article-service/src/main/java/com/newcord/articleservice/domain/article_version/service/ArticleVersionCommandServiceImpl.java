package com.newcord.articleservice.domain.article_version.service;

import com.newcord.articleservice.domain.article_version.entity.ArticleVersion;
import com.newcord.articleservice.domain.article_version.entity.VersionOperation;
import com.newcord.articleservice.domain.article_version.repository.ArticleVersionRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import java.util.ArrayList;
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
    public VersionOperation applyOperation(VersionOperation operation, ArticleVersion articleVersion, long operationIndex){
        synchronized (lock) {
            //버전상 충돌하는 경우 처리

            // position 수정하고 DB에 저장


            return operation;
        }
    }

    @Override
    public ArticleVersion createArticleVersion(Long articleId) {
        ArticleVersion articleVersion = ArticleVersion.builder()
            .id(articleId)
            .versions(new ArrayList<>())
            .build();

        articleVersionRepository.save(articleVersion);

        return articleVersion;
    }
}
