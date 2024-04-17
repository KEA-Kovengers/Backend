package com.newcord.articleservice.webSocket.interceptor;

import com.newcord.articleservice.rabbitMQ.Service.RabbitMQService;
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
public class ArticleEditInterceptor implements ChannelInterceptor {
    @Autowired
    private RabbitMQService rabbitMQService;

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

    private void handleSubscription(String destination, StompHeaderAccessor headerAccessor) {
        // 예를 들어, destination에 따라 다른 로직을 수행
        if (destination.startsWith("/topic/articleEditSession/")) {
            String sessionID = "editSession-" + destination.substring("/sub/articleEditSession/".length());
            rabbitMQService.createContainer(sessionID);
            System.out.println("Handling special subscription logic for: " + destination);
            // 특정 작업 수행
        }
    }
}
