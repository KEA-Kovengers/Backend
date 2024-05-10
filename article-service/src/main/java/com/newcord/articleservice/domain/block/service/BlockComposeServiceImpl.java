package com.newcord.articleservice.domain.block.service;

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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockComposeServiceImpl implements BlockComposeService{
    private final BlockCommandService blockCommandService;
    private final EditorQueryService editorQueryService;
    private final ArticlesCommandService articlesCommandService;

    @Override
    public BlockCreateResponseDTO createBlock(String userID, BlockCreateRequestDTO blockCreateDTO, Long postId) {
        //권한 확인
        editorQueryService.getEditorByPostIdAndUserID(postId, userID);

        //block 생성
        Block block = blockCommandService.createBlock(blockCreateDTO, postId);

        // Article의 blockList에 block 추가
        Article article = articlesCommandService.insertBlock(postId, InsertBlockRequestDTO.builder()
                .block(block)
                .position(blockCreateDTO.getPosition())
            .build());

        return BlockCreateResponseDTO.builder()
            .articleId(postId)
            .blockDTO(BlockDTO.toDTO(block))
            .position(blockCreateDTO.getPosition())
            .blockList(article.getBlock_list())
            .build();
    }

    @Override
    public BlockContentUpdateResponseDTO updateBlock(String userID,
        BlockContentUpdateRequestDTO blockContentUpdateDTO, Long postId) {
        // 권한 확인
        editorQueryService.getEditorByPostIdAndUserID(postId, userID);

        Block block = blockCommandService.updateBlock(blockContentUpdateDTO);

        return BlockContentUpdateResponseDTO.builder()
            .blockDTO(BlockDTO.toDTO(block))
            .updated_by(block.getUpdated_by())
            .build();
    }

    @Override
    public BlockDeleteResponseDTO deleteBlock(String userID, BlockDeleteRequestDTO blockDeleteDTO, Long postId) {
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
