package com.newcord.articleservice.domain.articles.service;

import com.newcord.articleservice.domain.articles.entity.Article;
import com.newcord.articleservice.domain.articles.repository.ArticlesRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticlesQueryServiceImpl implements ArticlesQueryService{
    private final ArticlesRepository articlesRepository;
    @Override
    public Article findArticleById(Long articleId) {
        return articlesRepository.findById(articleId).orElseThrow(() -> new ApiException(ErrorStatus._ARTICLE_NOT_FOUND));
    }
}
