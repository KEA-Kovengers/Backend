package com.newcord.noticeservice.domain.controller;

import com.newcord.noticeservice.domain.dto.NoticesRequest.NoticesRequestDTO;
import com.newcord.noticeservice.domain.entity.Notices;
import com.newcord.noticeservice.domain.service.NoticesService;
import com.newcord.noticeservice.rabbitMQ.RabbitMQService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.handler.annotation.MessageMapping;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NoticesController {

    private final NoticesService noticesService;
    private final RabbitMQService rabbitMQService;

    @PostMapping("/test")
    public Notices createNotice(@RequestBody Notices notices) {
        return noticesService.saveNotices(notices);
    }

    @MessageMapping("/send/{receiverId}")
    public String addNotices(@Payload String message, @DestinationVariable String receiverId) {
        // RabbitMQ의 Exchange로 메시지 전송
        log.info("Message sent to RabbitMQ: UserID {}", receiverId);
        rabbitMQService.sendMessage(receiverId, receiverId, message);
        return "Block updated";
    }
}