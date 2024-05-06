package com.newcord.articleservice.domain.block.controller;

import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockContentUpdateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockCreateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockContentUpdateResponseDTO;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockCreateResponseDTO;
import com.newcord.articleservice.domain.block.service.BlockCommandService;
import com.newcord.articleservice.global.common.WSRequest;
import com.newcord.articleservice.global.common.response.ApiResponse;
import com.newcord.articleservice.global.common.response.WSResponse;
import com.newcord.articleservice.rabbitMQ.Service.RabbitMQService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BlockController {
    private final RabbitMQService rabbitMQService;
    private final BlockCommandService blockCommandService;

    @MessageMapping("/updateBlock/{postID}")
    public WSResponse<BlockContentUpdateResponseDTO> updateBlock(WSRequest<BlockContentUpdateRequestDTO> requestDTO, @DestinationVariable Long postID) {
        BlockContentUpdateResponseDTO responseDTO = blockCommandService.updateBlock(requestDTO.getDto(), postID);
        WSResponse<BlockContentUpdateResponseDTO> response = WSResponse.onSuccess("/updateBlock/"+postID, requestDTO.getUuid(), responseDTO);

        rabbitMQService.sendMessage(postID.toString(), "", response);

        return response;
    }

    @MessageMapping("/createBlock/{postID}")
    public WSResponse<BlockCreateResponseDTO> createBlock(WSRequest<BlockCreateRequestDTO> blockCreateRequestDTO, @DestinationVariable Long postID) {
        BlockCreateResponseDTO responseDTO = blockCommandService.createBlock(blockCreateRequestDTO.getDto(), postID);
        WSResponse<BlockCreateResponseDTO> response = WSResponse.onSuccess("/createBlock/"+postID, blockCreateRequestDTO.getUuid(), responseDTO);
        rabbitMQService.sendMessage(postID.toString(), "", response);

        return response;
    }
}
