package com.newcord.articleservice.domain.block.controller;

import com.mysql.cj.log.Log;
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

    @MessageMapping("/updateBlock")
    @SendTo("/topic")
    public String updateBlock(String message) {
        /*
            * 블록 업데이트 로직
         */

        // RabbitMQ의 Exchange로 메시지 전송
        log.info("Message sent to RabbitMQ");
        rabbitTemplate.convertAndSend("1234", "", "Hello RabbitMQ");

        return "Hello RabbitMQ";
    }

//    @MessageMapping("/updateBlock/{postID}")
//    @SendTo("/topic/{postID}")
//    public String updateBlock(@Payload String message, @DestinationVariable String postID) {
//        /*
//         * 블록 업데이트 로직
//         */
//        // 메시지를 postID에 해당하는 Exchange로 전송
//        rabbitTemplate.convertAndSend(postID, "", message);
//
////        return message; // return값이 MQ의 Topic으로 전달됨
//    }
}
