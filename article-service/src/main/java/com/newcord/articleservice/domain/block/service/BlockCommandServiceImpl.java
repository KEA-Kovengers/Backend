package com.newcord.articleservice.domain.block.service;

import com.newcord.articleservice.domain.article_version.entity.OperationEntityType;
import com.newcord.articleservice.domain.article_version.entity.OperationType;
import com.newcord.articleservice.domain.articles.dto.ArticleRequest.InsertBlockRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.BlockSequenceUpdateResponseDTO;
import com.newcord.articleservice.domain.articles.service.ArticlesCommandService;
import com.newcord.articleservice.domain.articles.service.ArticlesQueryService;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockContentUpdateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockCreateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockDeleteRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockContentUpdateResponseDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockCreateResponseDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockDeleteResponseDTO;
import com.newcord.articleservice.domain.block.entity.Block;
import com.newcord.articleservice.domain.block.entity.BlockParent;
import com.newcord.articleservice.domain.block.entity.BlockUpdatedBy;
import com.newcord.articleservice.domain.block.repository.BlockRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockCommandServiceImpl implements BlockCommandService{
    private final BlockRepository blockRepository;

    @Override
    public Block createBlock(BlockCreateRequestDTO blockCreateDTO, Long postId) {
        blockCreateDTO.setArticleID(postId);
        // Block 생성
        BlockUpdatedBy blockUpdatedBy = BlockUpdatedBy.builder()
                .updated_at(blockCreateDTO.getCreated_by().getCreated_at())
                .updater_id(blockCreateDTO.getCreated_by().getCreator_id())
                .build();
        Block block = Block.builder()
                .content(blockCreateDTO.getContent())
                .parent(BlockParent.builder()
                    .type(blockCreateDTO.getBlockParent().getType())
                    .page_id(blockCreateDTO.getBlockParent().getPage_id())
                    .build())
                .created_by(blockCreateDTO.getCreated_by())
                .updated_by(blockUpdatedBy)
                .build();

        // Block 저장
        blockRepository.save(block);

        return block;
    }

    @Override
    public Block updateBlock(BlockContentUpdateRequestDTO blockContentUpdateDTO) {
        // 블록 업데이트 로직 후 ResponseDTO로 전송
        Block block = blockRepository.findById(new ObjectId(blockContentUpdateDTO.getBlockId())).orElseThrow(
                () -> new ApiException(ErrorStatus._BLOCK_NOT_FOUND)
        );

        // DTO로 받은 내용으로 블록 업데이트

        if(blockContentUpdateDTO.getEntityType().equals(OperationEntityType.TAG))
            block.updateContent(blockContentUpdateDTO.getContent(), blockContentUpdateDTO.getUpdated_by());
        //아래 부분은 position에 삽입하도록 수정
        else
        {
            StringBuffer sb = new StringBuffer(block.getContent());
            if(sb.length() < blockContentUpdateDTO.getPosition().intValue()){
                sb.setLength(blockContentUpdateDTO.getPosition().intValue());
                //문자열 연결하는데 인덱스 범위 초과하니 오류가 발생함
            }
            sb.insert(blockContentUpdateDTO.getPosition().intValue(), blockContentUpdateDTO.getContent());
            block.updateContent(sb.toString(), blockContentUpdateDTO.getUpdated_by());
        }


        // 블록 업데이트 후 저장
        blockRepository.save(block);

        return block;
    }

    @Override
    public Block deleteBlock(String blockID) {
        // 블록 삭제 로직 후 ResponseDTO로 전송
        Block block = blockRepository.findById(new ObjectId(blockID)).orElseThrow(
                () -> new ApiException(ErrorStatus._BLOCK_NOT_FOUND)
        );

        blockRepository.delete(block);

        return block;
    }
}
