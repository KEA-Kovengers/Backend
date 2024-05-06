package com.newcord.articleservice.domain.block.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockContentUpdateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockCreateRequestDTO;

public interface BlockCommandService {
    JSONPObject createBlock(BlockCreateRequestDTO blockCreateDTO, Long postId);        // 블럭 생성
    JSONPObject updateBlock(BlockContentUpdateRequestDTO blockContentUpdateDTO, Long postId);        // 블럭 내용 수정
}
