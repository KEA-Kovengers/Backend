package com.newcord.noticeservice.domain.controller;

import com.newcord.noticeservice.domain.entity.Notices;
import com.newcord.noticeservice.domain.service.NoticesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NoticesController {

    private final NoticesService noticesService;

    @PostMapping("/test")
    public Notices createNotice(@RequestBody Notices notices) {
        return noticesService.saveNotices(notices);
    }

}