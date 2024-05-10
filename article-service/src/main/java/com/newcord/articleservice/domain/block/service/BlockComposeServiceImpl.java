package com.newcord.articleservice.domain.block.service;

import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockContentUpdateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockCreateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockDeleteRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockContentUpdateResponseDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockCreateResponseDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockDeleteResponseDTO;

public class BlockComposeServiceImpl implements BlockComposeService{

    @Override
    public BlockCreateResponseDTO createBlock(BlockCreateRequestDTO blockCreateDTO, Long postId) {
        //block 생성

        // Article의 blockList에 block 추가
        return null;
    }

    @Override
    public BlockContentUpdateResponseDTO updateBlock(
        BlockContentUpdateRequestDTO blockContentUpdateDTO, Long postId) {
        // block update

        return null;
    }

    @Override
    public BlockDeleteResponseDTO deleteBlock(BlockDeleteRequestDTO blockDeleteDTO, Long postId) {
        // 블럭 삭제
        // Article의 blockList에서 block 삭제
        return null;
    }
}
