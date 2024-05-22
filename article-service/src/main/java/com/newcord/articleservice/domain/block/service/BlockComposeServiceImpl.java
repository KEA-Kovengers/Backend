package com.newcord.articleservice.domain.block.service;

import com.newcord.articleservice.domain.article_version.entity.ArticleVersion;
import com.newcord.articleservice.domain.article_version.entity.OperationType;
import com.newcord.articleservice.domain.article_version.entity.Version;
import com.newcord.articleservice.domain.article_version.entity.VersionOperation;
import com.newcord.articleservice.domain.article_version.service.ArticleVersionCommandService;
import com.newcord.articleservice.domain.article_version.service.ArticleVersionComposeService;
import com.newcord.articleservice.domain.articles.dto.ArticleRequest.InsertBlockRequestDTO;
import com.newcord.articleservice.domain.articles.entity.Article;
import com.newcord.articleservice.domain.articles.service.ArticlesCommandService;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockContentUpdateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockCreateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockDeleteRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockContentUpdateResponseDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockCreateResponseDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockDeleteResponseDTO;
import com.newcord.articleservice.domain.block.entity.Block;
import com.newcord.articleservice.domain.block.entity.BlockUpdatedBy;
import com.newcord.articleservice.domain.editor.service.EditorQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockComposeServiceImpl implements BlockComposeService{
    private final BlockCommandService blockCommandService;
    private final EditorQueryService editorQueryService;
    private final ArticlesCommandService articlesCommandService;
    private final ArticleVersionCommandService articleVersionCommandService;

    @Override
    public BlockCreateResponseDTO createBlock(Long userID, BlockCreateRequestDTO blockCreateDTO, Long postId) {
        //권한 확인
        editorQueryService.getEditorByPostIdAndUserID(postId, userID);

        //block 생성
        Block block = blockCommandService.createBlock(blockCreateDTO, postId);

        // ArticleVersion관련 로직 수행
        VersionOperation versionOperation = articleVersionCommandService.applyOperation(VersionOperation.builder()
                .id(block.getId())
                .operationType(OperationType.BLOCK_INSERT)
                .timestamp(blockCreateDTO.getCreated_by().getCreated_at())
                .position(blockCreateDTO.getPosition())
                .content(block.getContent())
                .updated_by(block.getUpdated_by())
            .build(), blockCreateDTO.getArticleVersion(), postId);


        // Article의 blockList에 block 추가
        Article article = articlesCommandService.insertBlock(postId, InsertBlockRequestDTO.builder()
                .block(block)
                .position(versionOperation.getPosition())
            .build());

        return BlockCreateResponseDTO.builder()
            .articleId(postId)
            .articleVersion(articleVersionCommandService.getLatestVersion(postId))
            .blockDTO(BlockDTO.toDTO(block))
            .position(blockCreateDTO.getPosition())
            .blockList(article.getBlock_list())
            .build();
    }

    @Override
    public BlockContentUpdateResponseDTO updateBlock(Long userID,
        BlockContentUpdateRequestDTO blockContentUpdateDTO, Long postId) {
        // 권한 확인
        editorQueryService.getEditorByPostIdAndUserID(postId, userID);

        // ArticleVersion관련 로직 수행
        VersionOperation versionOperation = articleVersionCommandService.applyOperation(VersionOperation.builder()
            .id(new ObjectId(blockContentUpdateDTO.getBlockId()))
            .operationType(blockContentUpdateDTO.getOperationType())
            .timestamp(blockContentUpdateDTO.getUpdated_by().getUpdated_at())
            .position(blockContentUpdateDTO.getPosition())
            .content(blockContentUpdateDTO.getContent())
            .updated_by(blockContentUpdateDTO.getUpdated_by())
            .build(), blockContentUpdateDTO.getArticleVersion(), postId);
        //====
        blockContentUpdateDTO.setPosition(versionOperation.getPosition());
        Block block = blockCommandService.updateBlock(blockContentUpdateDTO);

        return BlockContentUpdateResponseDTO.builder()
            .blockId(block.getId().toString())
            .articleVersion(articleVersionCommandService.getLatestVersion(postId))
            .position(blockContentUpdateDTO.getPosition())
            .updated_by(block.getUpdated_by())
            .build();
    }

    @Override
    public BlockDeleteResponseDTO deleteBlock(Long userID, BlockDeleteRequestDTO blockDeleteDTO, Long postId) {
        // 권한 확인
        editorQueryService.getEditorByPostIdAndUserID(postId, userID);

        // 블럭 삭제
        Block block = blockCommandService.deleteBlock(blockDeleteDTO.getBlockId());

        // Article의 blockList에서 block 삭제
        Article article = articlesCommandService.deleteBlockFromBlockList(postId, block);

        return BlockDeleteResponseDTO.builder()
            .blockId(block.getId().toString())
            .blockList(article.getBlock_list())
            .build();
    }
}
