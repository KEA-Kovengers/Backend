package com.newcord.articleservice.domain.block.service;

import com.newcord.articleservice.domain.block.dto.BlockResponse.*;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockDTO;

import java.util.List;

public interface BlockQueryService {
    BlockDTO getBlockDetail(String blockId);     //블럭 상세 검색 (ID 기반)

    List<BlockLogDataResponseDTO> getBlockCreator(Long creator_id);
}
