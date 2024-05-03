package com.newcord.articleservice.domain.block.controller;

import com.mysql.cj.log.Log;
import com.newcord.articleservice.domain.block.dto.BlockRequest;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockContentUpdateDTO;
import com.newcord.articleservice.domain.block.service.BlockCommandService;
import com.newcord.articleservice.global.common.response.ApiResponse;
import com.newcord.articleservice.rabbitMQ.Service.RabbitMQService;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
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
    public ApiResponse<String> updateBlock(BlockContentUpdateDTO blockContentUpdateDTO, @DestinationVariable String postID) {
        blockCommandService.updateBlock(blockContentUpdateDTO, postID);

        return ApiResponse.onSuccess("Block updated");
    }

    @MessageMapping("/createBlock/{postID}")
    public ApiResponse<String> createBlock(BlockContentUpdateDTO blockContentUpdateDTO, @DestinationVariable String postID) {
        blockCommandService.updateBlock(blockContentUpdateDTO, postID);

        return ApiResponse.onSuccess("Block updated");
    }
}