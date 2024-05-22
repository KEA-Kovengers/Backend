package com.newcord.articleservice.domain.articles.service;

import com.newcord.articleservice.domain.article_version.entity.VersionOperation;
import com.newcord.articleservice.domain.article_version.service.ArticleVersionCommandService;
import com.newcord.articleservice.domain.articles.dto.ArticleRequest.BlockSequenceUpdateRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.BlockSequenceUpdateResponseDTO;
import com.newcord.articleservice.domain.articles.entity.Article;
import com.newcord.articleservice.domain.editor.service.EditorQueryService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleComposeServiceImpl implements ArticleComposeService{
    private final ArticlesCommandService articlesCommandService;
    private final EditorQueryService editorQueryService;
    private final ArticleVersionCommandService articleVersionCommandService;

    @Override
    public BlockSequenceUpdateResponseDTO updateBlockSequence(Long userID, Long articleID,
        BlockSequenceUpdateRequestDTO blockSequenceUpdateRequestDTO) {
        // 권한 확인
        editorQueryService.getEditorByPostIdAndUserID(articleID, userID);

        //===========
        // ArticleVersion관련 로직 수행
        VersionOperation versionOperation = articleVersionCommandService.applyOperation(VersionOperation.builder()
            .id(new ObjectId(blockSequenceUpdateRequestDTO.getBlockID()))
            .operationType(blockSequenceUpdateRequestDTO.getOperationType())
            .timestamp(blockSequenceUpdateRequestDTO.getUpdated_by().getUpdated_at())
            .position(blockSequenceUpdateRequestDTO.getPosition())
            .content("")
            .updated_by(blockSequenceUpdateRequestDTO.getUpdated_by())
            .build(), blockSequenceUpdateRequestDTO.getArticleVersion(), articleID);
        //====
        //===========

        Article article = articlesCommandService.updateBlockSequence(articleID, blockSequenceUpdateRequestDTO);
        return BlockSequenceUpdateResponseDTO.builder()
            .articleId(article.getId())
            .blockID(blockSequenceUpdateRequestDTO.getBlockID())
            .position(versionOperation.getPosition())
            .operationType(versionOperation.getOperationType())
            .articleVersion(articleVersionCommandService.getLatestVersion(articleID))
            .blockList(article.getBlock_list())
            .updated_by(versionOperation.getUpdated_by())
            .build();
    }

}
