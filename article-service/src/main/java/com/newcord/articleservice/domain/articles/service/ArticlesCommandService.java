package com.newcord.articleservice.domain.articles.service;

import com.newcord.articleservice.domain.articles.dto.ArticleRequest.ArticleCreateRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleRequest.BlockSequenceUpdateRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleRequest.InsertBlockRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.ArticleCreateResponseDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.BlockSequenceUpdateResponseDTO;

public interface ArticlesCommandService {
    ArticleCreateResponseDTO createArticle(ArticleCreateRequestDTO articleCreateRequestDTO);            // 게시글 생성(MongoDB용)
    BlockSequenceUpdateResponseDTO insertBlock(InsertBlockRequestDTO insertBlockRequestDTO);            // 새로운 블럭을 게시글에 추가할때 사용
//    BlockSequenceUpdateResponseDTO updateBlockSequence(
//        BlockSequenceUpdateRequestDTO blockSequenceUpdateRequestDTO);        // 게시글 내 블럭 순서 변경

}
