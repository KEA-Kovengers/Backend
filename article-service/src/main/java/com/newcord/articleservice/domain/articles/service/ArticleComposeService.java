package com.newcord.articleservice.domain.articles.service;

import com.newcord.articleservice.domain.articles.dto.ArticleRequest.BlockSequenceUpdateRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleRequest.InsertBlockRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.ArticleCreateResponseDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.BlockSequenceUpdateResponseDTO;
import com.newcord.articleservice.domain.block.entity.Block;
import java.util.List;

public interface ArticleComposeService {
    BlockSequenceUpdateResponseDTO updateBlockSequence(Long userID, Long articleID,
        BlockSequenceUpdateRequestDTO blockSequenceUpdateRequestDTO);        // 게시글 내 블럭 순서 변경

}
