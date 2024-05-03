package com.newcord.articleservice.domain.block.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockContentUpdateDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockCreateDTO;
import com.newcord.articleservice.domain.block.entity.Block;
import com.newcord.articleservice.domain.block.entity.BlockUpdatedBy;
import com.newcord.articleservice.domain.block.repository.BlockRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import com.newcord.articleservice.rabbitMQ.Service.RabbitMQService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockCommandServiceImpl implements BlockCommandService{
    @Autowired
    private RabbitMQService rabbitMQService;
    private final BlockRepository blockRepository;

    @Override
    public JSONPObject createBlock(BlockCreateDTO blockCreateDTO, String postId) {
        BlockUpdatedBy blockUpdatedBy = BlockUpdatedBy.builder()
                .updated_at(blockCreateDTO.getCreated_by().getCreated_at())
                .updater_id(blockCreateDTO.getCreated_by().getCreator_id())
                .build();

        Block block = Block.builder()
                .content(blockCreateDTO.getContent())
                .blockType(blockCreateDTO.getBlockType())
                .created_by(blockCreateDTO.getCreated_by())
                .updated_by(blockUpdatedBy)
                .build();

        blockRepository.save(block);

        rabbitMQService.sendMessage(postId, "", blockCreateDTO);

        return null;
    }

    @Override
    public JSONPObject updateBlock(BlockContentUpdateDTO blockContentUpdateDTO, String postId) {
        // 블록 업데이트 로직 후 ResponseDTO로 전송
        Block block = blockRepository.findById(blockContentUpdateDTO.getBlockId()).orElseThrow(
                () -> new ApiException(ErrorStatus._BLOCK_NOT_FOUND)
        );

        //TODO: 컨플릭트 로직이 들어가야함


        // 블록 생성으로 DB에 저장하는 API구현하고, 웹소켓으로 업데이트하는게 제대로 반영 되는지 확인

        // DTO로 받은 내용으로 블록 업데이트
        block.updateContent(blockContentUpdateDTO.getContent(), blockContentUpdateDTO.getUpdated_by());
        block.updateBlockType(blockContentUpdateDTO.getBlockType(), blockContentUpdateDTO.getUpdated_by());

        // 블록 업데이트 후 저장
        blockRepository.save(block);

        rabbitMQService.sendMessage(postId, "", blockContentUpdateDTO);
        return null;
    }
}