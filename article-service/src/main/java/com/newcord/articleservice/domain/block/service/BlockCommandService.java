package com.newcord.articleservice.domain.block.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockContentUpdateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockCreateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockDeleteRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockContentUpdateResponseDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockCreateResponseDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockDeleteResponseDTO;
import com.newcord.articleservice.domain.block.entity.Block;


// Block도메인에 대한 command 모듈 서비스이다. 저수준의 인터페이스 계층을 구현한다.
public interface BlockCommandService {
    Block createBlock(BlockCreateRequestDTO blockCreateDTO, Long postId);        // 블럭 생성
    Block updateBlock(BlockContentUpdateRequestDTO blockContentUpdateDTO);        // 블럭 내용 수정
    Block deleteBlock(String blockID);        // 블럭 삭제
}
