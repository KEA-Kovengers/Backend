package com.newcord.articleservice.domain.articles.service;

import com.newcord.articleservice.domain.articles.dto.ArticleRequest.BlockSequenceUpdateRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleRequest.InsertBlockRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.ArticleCreateResponseDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.BlockSequenceUpdateResponseDTO;
import com.newcord.articleservice.domain.articles.entity.Article;
import com.newcord.articleservice.domain.articles.repository.ArticlesRepository;
import com.newcord.articleservice.domain.block.entity.Block;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticlesCommandServiceImpl implements ArticlesCommandService{
    private final ArticlesRepository articlesRepository;

    @Override
    public Article createArticle(Long articleID) {
        articlesRepository.findById(articleID)
            .ifPresent(a -> {
                throw new ApiException(ErrorStatus._ARTICLE_ALREADY_EXISTS);
            });

        Article article = Article.builder()
            .id(articleID)
            .block_list(new ArrayList<>())
            .build();

        articlesRepository.save(article);

        return article;
    }

    @Override
    public Article insertBlock(Long articleID, InsertBlockRequestDTO insertBlockRequestDTO) {
        Article article = articlesRepository.findById(articleID)
            .orElseThrow(() -> new ApiException(ErrorStatus._ARTICLE_NOT_FOUND));

        if(article.getBlock_list().contains(insertBlockRequestDTO.getBlock().getId().toString()))
            throw new ApiException(ErrorStatus._BLOCK_ALREADY_EXISTS);

        article.getBlock_list().add(insertBlockRequestDTO.getPosition().intValue(), insertBlockRequestDTO.getBlock().getId().toString());

        articlesRepository.save(article);

        return article;
    }

    @Override
    public Article updateBlockSequence(
        Long articleID,
        BlockSequenceUpdateRequestDTO blockSequenceUpdateRequestDTO) {
        Article article = articlesRepository.findById(articleID)
            .orElseThrow(() -> new ApiException(ErrorStatus._ARTICLE_NOT_FOUND));

        for (int i = 0; i < blockSequenceUpdateRequestDTO.getBlockList().size(); i++) {
            int idx = article.getBlock_list().indexOf(blockSequenceUpdateRequestDTO.getBlockList().get(i));
            if(idx == -1)
                throw new ApiException(ErrorStatus._BLOCK_NOT_FOUND);

            // 원래 있던 블럭을 삭제하고 새로운 위치에 추가
            article.getBlock_list().remove(idx);
            article.getBlock_list().add(blockSequenceUpdateRequestDTO.getPosition().get(i).intValue(),
                blockSequenceUpdateRequestDTO.getBlockList().get(i));
        }

        articlesRepository.save(article);

        return article;
    }

    @Override
    public Article deleteBlockFromBlockList(Long articleID, Block block) {
        Article article = articlesRepository.findById(articleID)
            .orElseThrow(() -> new ApiException(ErrorStatus._ARTICLE_NOT_FOUND));

        article.getBlock_list().remove(block.getId().toString());
        articlesRepository.save(article);

        return article;
    }

    @Override
    public Article deleteArticle(Long articleID) {
        Article article = articlesRepository.findById(articleID)
            .orElseThrow(() -> new ApiException(ErrorStatus._ARTICLE_NOT_FOUND));

        articlesRepository.delete(article);

        return article;
    }
}
