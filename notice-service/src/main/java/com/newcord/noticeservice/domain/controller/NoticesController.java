package com.newcord.noticeservice.domain.controller;

import com.newcord.noticeservice.domain.dto.NoticesRequest.NoticesRequestDTO;
import com.newcord.noticeservice.domain.dto.NoticesResponse;
import com.newcord.noticeservice.domain.entity.Notices;
import com.newcord.noticeservice.domain.service.NoticesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.handler.annotation.MessageMapping;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NoticesController {

    private final NoticesService noticesService;
    private final RabbitTemplate rabbitTemplate;

    @PostMapping("/test")
    public Notices createNotice(@RequestBody Notices notices) {
        return noticesService.saveNotices(notices);
    }

    private final static String EXCHANGE_NAME = "notice.exchange";
    private final static String QUEUE_NAME = "notice.queue";

    @MessageMapping("notice.message.{userId}")
    public void sendMessage(@Payload NoticesRequestDTO noticesRequestDTO, @DestinationVariable String userId) {
        noticesRequestDTO.setCreated_at(LocalDateTime.now());
        noticesRequestDTO.setBody(noticesRequestDTO.getBody());
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "notice." + userId, noticesRequestDTO);
    }
}