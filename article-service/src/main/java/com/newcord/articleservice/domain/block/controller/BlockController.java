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

    @MessageMapping("/updateBlock/{postID}")
    public ApiResponse<String> updateBlock(BlockContentUpdateDTO blockContentUpdateDTO, @DestinationVariable String postID) {
        /*
            * 블록 업데이트 로직
         */


        blockCommandService.updateBlock(blockContentUpdateDTO, postID);

        return ApiResponse.onSuccess("Block updated");
    }
}
