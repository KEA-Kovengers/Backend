package com.newcord.articleservice.domain.articles.service;

import com.newcord.articleservice.domain.articles.dto.ArticleRequest.BlockSequenceUpdateRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleRequest.InsertBlockRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.ArticleCreateResponseDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.BlockSequenceUpdateResponseDTO;
import com.newcord.articleservice.domain.articles.entity.Article;
import com.newcord.articleservice.domain.block.entity.Block;
import java.util.List;

public interface ArticlesCommandService {
    Article createArticle(Long articleID);            // 게시글 생성(MongoDB용)
    Article insertBlock(Long articleID, InsertBlockRequestDTO insertBlockRequestDTO);            // 새로운 블럭을 게시글에 추가할때 사용
    Article updateBlockSequence(Long articleID,
        BlockSequenceUpdateRequestDTO blockSequenceUpdateRequestDTO);        // 게시글 내 블럭 순서 변경
    Article deleteBlockFromBlockList(Long articleID, Block block);            // 게시글 내 블럭 삭제
    Article deleteArticle(Long articleID);            // 게시글 삭제



}
