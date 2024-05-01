package com.newcord.articleservice.webSocket.interceptor;

import com.newcord.articleservice.rabbitMQ.Service.RabbitMQService;
import com.newcord.articleservice.webSocket.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ArticleEditInterceptor implements ChannelInterceptor {

    private final RabbitMQService rabbitMQService;

//    private final WebSocketService webSocketService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // 인바운드 메시지를 가로채는 로직
        System.out.println("Inbound Message: " + message);
        if (message == null) {
            return message;
        }

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        if (headerAccessor.getCommand() != StompCommand.SUBSCRIBE){
            return message;
        }

        // 구독 대상 destination 추출
        String destination = headerAccessor.getDestination();
        if (destination == null) {
            return message;
        }
        log.info("Received subscription for: " + destination);

        // 필요한 작업 수행
        handleSubscription(destination, headerAccessor);
        return message;
    }

    // Client가 Exchange로 구독을 요청하면 해당 Exchange를 생성
    private void handleSubscription(String destination, StompHeaderAccessor headerAccessor) {
        if (destination.startsWith("/exchange")) {
            String[] sessionID = destination.split("/");
            rabbitMQService.createTopic(sessionID[2]);
        }
    }
}
