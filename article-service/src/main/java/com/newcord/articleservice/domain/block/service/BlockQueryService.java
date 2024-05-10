package com.newcord.articleservice.domain.block.service;

import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockDTO;

public interface BlockQueryService {
    BlockDTO getBlockDetail(String blockId);     //블럭 상세 검색 (ID 기반)

}
