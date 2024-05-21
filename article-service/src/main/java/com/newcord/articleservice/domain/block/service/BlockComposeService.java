package com.newcord.articleservice.domain.block.service;

import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockContentUpdateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockCreateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockDeleteRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockContentUpdateResponseDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockCreateResponseDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockDeleteResponseDTO;

public interface BlockComposeService {
    BlockCreateResponseDTO createBlock(Long userID, BlockCreateRequestDTO blockCreateDTO, Long postId);        // 블럭 생성
    BlockContentUpdateResponseDTO updateBlock(Long userID, BlockContentUpdateRequestDTO blockContentUpdateDTO, Long postId);        // 블럭 내용 수정
    BlockDeleteResponseDTO deleteBlock(Long userID, BlockDeleteRequestDTO blockDeleteDTO, Long postId);        // 블럭 삭제

}
