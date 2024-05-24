package com.newcord.noticeservice.domain.service;

import com.newcord.noticeservice.domain.entity.Notices;
import com.newcord.noticeservice.domain.repository.NoticesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public interface NoticesService {
    Notices saveNotices(Notices notices); // 테스트
    SseEmitter subscribe(String receiver_id, String lastEventId); // 구독 설정
}