package com.newcord.articleservice.domain.block.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockContentUpdateDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockCreateDTO;

public interface BlockCommandService {
    JSONPObject createBlock(BlockCreateDTO blockCreateDTO, String postId);        // 블럭 생성
    JSONPObject updateBlock(BlockContentUpdateDTO blockContentUpdateDTO, String postId);        // 블럭 내용 수정
}
