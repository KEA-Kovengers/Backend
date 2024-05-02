package com.newcord.articleservice.domain.block.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockContentUpdateDTO;

public interface BlockCommandService {
    JSONPObject updateBlock(BlockContentUpdateDTO blockContentUpdateDTO, String postId);
}
