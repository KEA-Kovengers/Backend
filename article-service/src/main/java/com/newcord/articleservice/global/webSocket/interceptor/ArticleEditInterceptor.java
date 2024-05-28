package com.newcord.articleservice.global.webSocket.interceptor;

import com.newcord.articleservice.global.rabbitMQ.Service.RabbitMQService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.scheduling.annotation.Scheduled;
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

        System.out.println("running");

        if (message == null) {
            return message;
        }

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        if (headerAccessor.getCommand() != StompCommand.SUBSCRIBE){
            return message;
        }

        // 구독 대상 destination 추출
        String destination = headerAccessor.getDestination();
        System.out.println("destination = " + destination);
        if (destination == null) {
            return message;
        }
        log.info("Received subscription for: " + destination);

        // 필요한 작업 수행
        handleSubscription(destination, headerAccessor);
        return message;
    }


    @Scheduled(fixedRate = 1000)
    public void increaseId(){
        //increase id if article id exist

    }


    // Client가 Exchange로 구독을 요청하면 해당 Exchange를 생성
    private void handleSubscription(String destination, StompHeaderAccessor headerAccessor) {
        if (destination.startsWith("/exchange")) {
            String[] sessionID = destination.split("/");
            rabbitMQService.createFanoutExchange(sessionID[2]);
        }
    }
}
