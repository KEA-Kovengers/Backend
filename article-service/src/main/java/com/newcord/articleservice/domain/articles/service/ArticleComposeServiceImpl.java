package com.newcord.articleservice.domain.articles.service;

import com.newcord.articleservice.domain.articles.dto.ArticleRequest.BlockSequenceUpdateRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleRequest.InsertBlockRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.ArticleCreateResponseDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.BlockSequenceUpdateResponseDTO;
import com.newcord.articleservice.domain.articles.entity.Article;
import com.newcord.articleservice.domain.block.entity.Block;
import com.newcord.articleservice.domain.block.service.BlockCommandService;
import com.newcord.articleservice.domain.editor.service.EditorCommandService;
import com.newcord.articleservice.domain.editor.service.EditorQueryService;
import com.newcord.articleservice.rabbitMQ.Service.RabbitMQService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleComposeServiceImpl implements ArticleComposeService{
    private final ArticlesCommandService articlesCommandService;
    private final EditorQueryService editorQueryService;

    @Override
    public BlockSequenceUpdateResponseDTO updateBlockSequence(String userID, Long articleID,
        BlockSequenceUpdateRequestDTO blockSequenceUpdateRequestDTO) {
        // 권한 확인
        editorQueryService.getEditorByPostIdAndUserID(articleID, userID);

        Article article = articlesCommandService.updateBlockSequence(articleID, blockSequenceUpdateRequestDTO);
        return BlockSequenceUpdateResponseDTO.builder()
            .articleId(article.getId())
            .blockList(article.getBlock_list())
            .build();
    }

}
