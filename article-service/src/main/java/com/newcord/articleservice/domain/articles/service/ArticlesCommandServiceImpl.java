package com.newcord.articleservice.domain.articles.service;

import com.newcord.articleservice.domain.articles.dto.ArticleRequest.ArticleCreateRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleRequest.InsertBlockRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.ArticleCreateResponseDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.BlockSequenceUpdateResponseDTO;
import com.newcord.articleservice.domain.articles.entity.Article;
import com.newcord.articleservice.domain.articles.repository.ArticlesRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticlesCommandServiceImpl implements ArticlesCommandService{
    private final ArticlesRepository articlesRepository;
    private final ArticlesQueryService articlesQueryService;

    @Override
    public ArticleCreateResponseDTO createArticle(ArticleCreateRequestDTO articleCreateRequestDTO) {
        Article article = Article.builder()
            .id(articleCreateRequestDTO.getArticleId())
            .block_list(new ArrayList<>())
            .build();

        if(!articlesRepository.findById(articleCreateRequestDTO.getArticleId()).isEmpty())
            throw new ApiException(ErrorStatus._ARTICLE_ALREADY_EXISTS);

        articlesRepository.save(article);

        return ArticleCreateResponseDTO.builder()
            .articleId(article.getId())
            .build();
    }

    @Override
    public BlockSequenceUpdateResponseDTO insertBlock(InsertBlockRequestDTO insertBlockRequestDTO) {
        Article article = articlesQueryService.findArticleById(insertBlockRequestDTO.getArticleId());

        if(article.getBlock_list().contains(insertBlockRequestDTO.getBlock().getId()))
            throw new ApiException(ErrorStatus._BLOCK_ALREADY_EXISTS);

        article.getBlock_list().add(insertBlockRequestDTO.getPosition().intValue(), insertBlockRequestDTO.getBlock().getId().toString());

        articlesRepository.save(article);

        return BlockSequenceUpdateResponseDTO.builder()
            .articleId(article.getId())
            .block(insertBlockRequestDTO.getBlock())
            .newPosition(insertBlockRequestDTO.getPosition())
            .blockList(article.getBlock_list())
            .build();
    }
}
