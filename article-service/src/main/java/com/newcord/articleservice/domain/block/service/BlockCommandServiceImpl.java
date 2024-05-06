package com.newcord.articleservice.domain.block.service;

import com.newcord.articleservice.domain.articles.dto.ArticleRequest.InsertBlockRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.BlockSequenceUpdateResponseDTO;
import com.newcord.articleservice.domain.articles.service.ArticlesCommandService;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockContentUpdateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockCreateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockContentUpdateResponseDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockCreateResponseDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockDTO;
import com.newcord.articleservice.domain.block.entity.Block;
import com.newcord.articleservice.domain.block.entity.BlockParent;
import com.newcord.articleservice.domain.block.entity.BlockUpdatedBy;
import com.newcord.articleservice.domain.block.repository.BlockRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockCommandServiceImpl implements BlockCommandService{
    private final BlockRepository blockRepository;
    private final ArticlesCommandService articlesCommandService;

    @Override
    public BlockCreateResponseDTO createBlock(BlockCreateRequestDTO blockCreateDTO, Long postId) {
        blockCreateDTO.setArticleID(postId);
        // Block 생성
        BlockUpdatedBy blockUpdatedBy = BlockUpdatedBy.builder()
                .updated_at(blockCreateDTO.getCreated_by().getCreated_at())
                .updater_id(blockCreateDTO.getCreated_by().getCreator_id())
                .build();
        Block block = Block.builder()
                .content(blockCreateDTO.getContent())
                .blockType(blockCreateDTO.getBlockType())
                .parent(BlockParent.builder()
                    .type(blockCreateDTO.getBlockParent().getType())
                    .page_id(blockCreateDTO.getBlockParent().getPage_id())
                    .build())
                .created_by(blockCreateDTO.getCreated_by())
                .updated_by(blockUpdatedBy)
                .build();

        // Block 저장
        blockRepository.save(block);

        // Article에 순서에 맞게 Insert
        BlockSequenceUpdateResponseDTO responseDTO = articlesCommandService.insertBlock(InsertBlockRequestDTO.builder()
            .articleId(blockCreateDTO.getArticleID())
            .block(block)
            .position(blockCreateDTO.getPosition())
            .build());

        return BlockCreateResponseDTO.builder()
            .blockDTO(BlockDTO.toDTO(block))
            .articleId(blockCreateDTO.getArticleID())
            .position(blockCreateDTO.getPosition())
            .blockList(responseDTO.getBlockList())
            .build();
    }

    @Override
    public BlockContentUpdateResponseDTO updateBlock(BlockContentUpdateRequestDTO blockContentUpdateDTO, Long postId) {
        // 블록 업데이트 로직 후 ResponseDTO로 전송
        Block block = blockRepository.findById(new ObjectId(blockContentUpdateDTO.getBlockId())).orElseThrow(
                () -> new ApiException(ErrorStatus._BLOCK_NOT_FOUND)
        );

        //TODO: 컨플릭트 로직이 들어가야함


        // 블록 생성으로 DB에 저장하는 API구현하고, 웹소켓으로 업데이트하는게 제대로 반영 되는지 확인

        // DTO로 받은 내용으로 블록 업데이트
        block.updateContent(blockContentUpdateDTO.getContent(), blockContentUpdateDTO.getUpdated_by());
        block.updateBlockType(blockContentUpdateDTO.getBlockType(), blockContentUpdateDTO.getUpdated_by());

        // 블록 업데이트 후 저장
        blockRepository.save(block);

        return BlockContentUpdateResponseDTO.builder()
            .blockId(block.getId().toString())
            .blockType(block.getBlockType())
            .position(blockContentUpdateDTO.getPosition())
            .content(block.getContent())
            .updated_by(block.getUpdated_by())
            .build();
    }
}
