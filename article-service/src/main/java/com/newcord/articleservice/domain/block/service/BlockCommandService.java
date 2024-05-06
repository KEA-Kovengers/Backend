package com.newcord.articleservice.domain.block.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockContentUpdateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockCreateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockContentUpdateResponseDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockCreateResponseDTO;

public interface BlockCommandService {
    BlockCreateResponseDTO createBlock(BlockCreateRequestDTO blockCreateDTO, Long postId);        // 블럭 생성
    BlockContentUpdateResponseDTO updateBlock(BlockContentUpdateRequestDTO blockContentUpdateDTO, Long postId);        // 블럭 내용 수정
}
