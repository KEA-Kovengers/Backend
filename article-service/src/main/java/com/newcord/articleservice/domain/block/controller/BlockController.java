package com.newcord.articleservice.domain.block.controller;

import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockContentUpdateRequestDTO;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockCreateRequestDTO;
import com.newcord.articleservice.domain.block.service.BlockCommandService;
import com.newcord.articleservice.global.common.response.ApiResponse;
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

    /*
     게시글 블럭 순서 수정 구현, MongoDB 블럭 생성 반영되는지
     */
    @MessageMapping("/updateBlock/{postID}")
    public ApiResponse<String> updateBlock(BlockContentUpdateRequestDTO blockContentUpdateDTO, @DestinationVariable String postID) {
        blockCommandService.updateBlock(blockContentUpdateDTO, postID);

        return ApiResponse.onSuccess("Block updated");
    }

    @MessageMapping("/createBlock/{postID}")
    public ApiResponse<String> createBlock(BlockCreateRequestDTO blockCreateRequestDTO, @DestinationVariable String postID) {
        blockCommandService.createBlock(blockCreateRequestDTO, postID);

        return ApiResponse.onSuccess("Block created");
    }
}
