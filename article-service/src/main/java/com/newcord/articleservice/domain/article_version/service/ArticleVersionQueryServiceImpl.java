package com.newcord.articleservice.domain.article_version.service;

import com.newcord.articleservice.domain.article_version.entity.ArticleVersion;
import com.newcord.articleservice.domain.article_version.repository.ArticleVersionRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleVersionQueryServiceImpl implements ArticleVersionQueryService{
    private final ArticleVersionRepository articleVersionRepository;

    @Override
    public ArticleVersion findArticleVersionById(Long articleId) {
        return articleVersionRepository.findById(articleId).orElseThrow(() -> new ApiException(ErrorStatus._ARTICLE_NOT_FOUND));
    }

}
