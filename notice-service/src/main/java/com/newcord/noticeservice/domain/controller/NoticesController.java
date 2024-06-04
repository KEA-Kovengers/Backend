package com.newcord.noticeservice.domain.controller;

import com.newcord.noticeservice.domain.dto.NoticesRequest.NoticesRequestDTO;
import com.newcord.noticeservice.domain.dto.NoticesResponse.NoticesResponseDTO;
import com.newcord.noticeservice.domain.service.NoticesCommandService;
import com.newcord.noticeservice.domain.service.NoticesQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import com.newcord.noticeservice.global.common.response.ApiResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Notices", description = "알림 API")
public class NoticesController {

    private final NoticesCommandService noticesCommandService;
    private final NoticesQueryService noticesQueryService;
    private final RabbitTemplate rabbitTemplate;
    private final static String EXCHANGE_NAME = "notice.exchange";

//    @MessageMapping("notice.message.{userId}")
    @PostMapping("/send/{userId}")
    public void sendMessage(@RequestBody NoticesRequestDTO noticesRequestDTO, @PathVariable String userId) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "notice." + userId, noticesRequestDTO);
    }

    @Operation(summary = "알림 저장", description = "유저가 받은 알림을 저장합니다.")
    @PostMapping("/addNotice")
    public ApiResponse<NoticesResponseDTO> addNotice(@RequestBody NoticesRequestDTO noticesRequestDTO) {
        return ApiResponse.onSuccess(noticesCommandService.addNotices(noticesRequestDTO));
    }

    @Operation(summary = "알림 조회", description = "유저가 받은 알림 목록을 조회합니다.")
    @GetMapping("/view/{userId}")
    public ApiResponse<List> getNoticeList(@PathVariable Long userId) {
        return ApiResponse.onSuccess(noticesQueryService.getNoticeList(userId));
    }

    @Operation(summary = "알림 읽음 처리", description = "유저가 읽은 알림을 읽음 처리합니다.")
    @PostMapping("/status/{noticeId}")
    public ApiResponse<NoticesResponseDTO> updateStatus(@PathVariable String noticeId) {
        return ApiResponse.onSuccess(noticesCommandService.updateStatus(noticeId));
    }
}