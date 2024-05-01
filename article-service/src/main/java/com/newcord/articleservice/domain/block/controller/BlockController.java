package com.newcord.articleservice.domain.block.controller;

import com.mysql.cj.log.Log;
import com.newcord.articleservice.global.common.response.ApiResponse;
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

    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @MessageMapping("/updateBlock/{postID}")
    public ApiResponse<String> updateBlock(@Payload String message, @DestinationVariable String postID) {
        /*
            * 블록 업데이트 로직
         */

        // RabbitMQ의 Exchange로 메시지 전송
        log.info("Message sent to RabbitMQ");
        rabbitTemplate.convertAndSend(postID, "", message);

        return ApiResponse.onSuccess("Block updated");
    }
}
