package com.newcord.articleservice.domain.articles.service;

import com.newcord.articleservice.domain.articles.dto.ArticleRequest.BlockSequenceUpdateRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.BlockSequenceUpdateResponseDTO;
import com.newcord.articleservice.domain.articles.entity.Article;
import com.newcord.articleservice.domain.editor.service.EditorQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleComposeServiceImpl implements ArticleComposeService{
    private final ArticlesCommandService articlesCommandService;
    private final EditorQueryService editorQueryService;

    @Override
    public BlockSequenceUpdateResponseDTO updateBlockSequence(Long userID, Long articleID,
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
