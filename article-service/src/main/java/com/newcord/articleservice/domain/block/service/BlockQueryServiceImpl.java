package com.newcord.articleservice.domain.block.service;

import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockDTO;
import com.newcord.articleservice.domain.block.entity.Block;
import com.newcord.articleservice.domain.block.repository.BlockRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockQueryServiceImpl implements BlockQueryService{
    private final BlockRepository blockRepository;
    @Override
    public BlockDTO getBlockDetail(String blockId) {
        Block block = blockRepository.findById(new ObjectId(blockId)).orElse(null);
        if(block == null){
            throw new ApiException(ErrorStatus._BLOCK_NOT_FOUND);
        }

        return BlockDTO.toDTO(block);
    }
}
