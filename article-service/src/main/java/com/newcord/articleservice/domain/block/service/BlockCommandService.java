package com.newcord.articleservice.domain.block.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockContentUpdateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockCreateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockDeleteRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockContentUpdateResponseDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockCreateResponseDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockDeleteResponseDTO;

public interface BlockCommandService {
    BlockCreateResponseDTO createBlock(BlockCreateRequestDTO blockCreateDTO, Long postId);        // 블럭 생성
    BlockContentUpdateResponseDTO updateBlock(BlockContentUpdateRequestDTO blockContentUpdateDTO, Long postId);        // 블럭 내용 수정
    BlockDeleteResponseDTO deleteBlock(BlockDeleteRequestDTO blockDeleteDTO, Long postId);        // 블럭 삭제
}
