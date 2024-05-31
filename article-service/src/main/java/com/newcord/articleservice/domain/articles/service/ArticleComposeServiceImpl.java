package com.newcord.articleservice.domain.articles.service;

import com.newcord.articleservice.domain.article_version.entity.VersionOperation;
import com.newcord.articleservice.domain.article_version.service.ArticleVersionCommandService;
import com.newcord.articleservice.domain.articles.dto.ArticleRequest.BlockSequenceUpdateRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleRequest.TitleUpdateRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.BlockSequenceUpdateResponseDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.TitleUpdateResponseDTO;
import com.newcord.articleservice.domain.articles.entity.Article;
import com.newcord.articleservice.domain.editor.service.EditorQueryService;
import com.newcord.articleservice.domain.posts.Service.PostsCommandService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleComposeServiceImpl implements ArticleComposeService{
    private final ArticlesCommandService articlesCommandService;
    private final EditorQueryService editorQueryService;
    private final ArticleVersionCommandService articleVersionCommandService;
    private final PostsCommandService postsCommandService;

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
            .entityType(blockSequenceUpdateRequestDTO.getEntityType())
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
            .entityType(versionOperation.getEntityType())
            .articleVersion(articleVersionCommandService.getLatestVersion(articleID))
            .blockList(article.getBlock_list())
            .updated_by(versionOperation.getUpdated_by())
            .build();
    }

    @Override
    public TitleUpdateResponseDTO updateTitle(Long userID, Long articleID,
        TitleUpdateRequestDTO titleUpdateRequestDTO) {
        // 권한 확인
        editorQueryService.getEditorByPostIdAndUserID(articleID, userID);

        //===========
        // ArticleVersion관련 로직 수행
        VersionOperation versionOperation = articleVersionCommandService.applyOperation(VersionOperation.builder()
            .id(null)
            .operationType(titleUpdateRequestDTO.getOperationType())
            .entityType(titleUpdateRequestDTO.getEntityType())
            .timestamp(titleUpdateRequestDTO.getUpdated_by().getUpdated_at())
            .position(titleUpdateRequestDTO.getPosition())
            .content(titleUpdateRequestDTO.getContent())
            .updated_by(titleUpdateRequestDTO.getUpdated_by())
            .build(), titleUpdateRequestDTO.getArticleVersion(), articleID);
        //====
        postsCommandService.updateTitle(userID, articleID, titleUpdateRequestDTO.getPosition().intValue(), titleUpdateRequestDTO.getContent());


        //===========
        return TitleUpdateResponseDTO.builder()
            .articleId(articleID)
            .articleVersion(articleVersionCommandService.getLatestVersion(articleID))
            .operationType(versionOperation.getOperationType())
            .entityType(versionOperation.getEntityType())
            .position(versionOperation.getPosition())
            .content(titleUpdateRequestDTO.getContent())
            .updated_by(versionOperation.getUpdated_by())
            .build();
    }
}
