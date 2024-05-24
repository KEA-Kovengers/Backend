package com.newcord.noticeservice.domain.service;

import com.newcord.noticeservice.domain.dto.NoticesDTO;
import com.newcord.noticeservice.domain.entity.NoticeType;
import com.newcord.noticeservice.domain.entity.Notices;
import com.newcord.noticeservice.domain.repository.EmitterRepository;
import com.newcord.noticeservice.domain.repository.NoticesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

import static com.newcord.noticeservice.domain.entity.StatusType.NOT_READ;

@Service
@RequiredArgsConstructor
public class NoticesServiceImpl implements NoticesService{
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final NoticesRepository noticesRepository;

    @Override
    public Notices saveNotices(Notices notices) {
        return noticesRepository.save(notices);
    }

    @Override
    public SseEmitter subscribe(String username, String lastEventId) {
        String emitterId = makeTimeIncludeId(username);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 503 에러를 방지하기 위한 더미 이벤트 전송
        String eventId = makeTimeIncludeId(username);
        sendNotification(emitter, eventId, emitterId, "EventStream Created. [userEmail=" + username + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, username, emitterId, emitter);
        }

        return emitter;
    }

    // 회원의 이벤트 중 시간으로 구분할 수 있게 id 생성
    private String makeTimeIncludeId(String email) {
        return email + "_" + System.currentTimeMillis();
    }

    // 이벤트 전송
    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("sse")
                    .data(data)
            );
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }

    // 구독자가 받지 못한 데이터 있는 확인
    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    // 받지 못한 데이터 있으면 전송
    private void sendLostData(String lastEventId, String userEmail, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(userEmail));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    // 지정된 수신자에게 알림 전송
    public void send(String receiver_id, NoticeType type, String body) {
        Notices notification = noticesRepository.save(createNotification(receiver_id, type, body));

        String eventId = receiver_id + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiver_id);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, NoticesDTO.NoticesResponseDTO.createResponse(notification));
                }
        );
    }

    private Notices createNotification(String receiver_id, NoticeType type, String body) {
        return Notices.builder()
                .user_id(Long.parseLong(receiver_id))
                .type(type)
                .body(body)
                .status(NOT_READ)
                .build();
    }

}
