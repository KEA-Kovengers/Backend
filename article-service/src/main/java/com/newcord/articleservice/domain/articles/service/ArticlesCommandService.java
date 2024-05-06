package com.newcord.articleservice.domain.articles.service;

import com.newcord.articleservice.domain.articles.dto.ArticleRequest.BlockSequenceUpdateRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleRequest.InsertBlockRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.ArticleCreateResponseDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.BlockSequenceUpdateResponseDTO;

public interface ArticlesCommandService {
    ArticleCreateResponseDTO createArticle(Long articleID);            // 게시글 생성(MongoDB용)
    BlockSequenceUpdateResponseDTO insertBlock(Long articleID, InsertBlockRequestDTO insertBlockRequestDTO);            // 새로운 블럭을 게시글에 추가할때 사용
    BlockSequenceUpdateResponseDTO updateBlockSequence(Long articleID,
        BlockSequenceUpdateRequestDTO blockSequenceUpdateRequestDTO);        // 게시글 내 블럭 순서 변경

}
