package com.newcord.noticeservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${spring.rabbitmq.host}")
    private String RELAY_HOST;
    @Value("${spring.rabbitmq.stomp-port}")
    private Integer RELAY_PORT;
    @Value("${spring.rabbitmq.username}")
    private String RELAY_USERNAME;
    @Value("${spring.rabbitmq.password}")
    private String RELAY_PASSWORD;
    @Value("${spring.rabbitmq.virtual-host}")
    private String VIRTUAL_HOST;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 외부의 RabbitMQ 브로커를 사용하기 위한 설정
        // TOPIC만 사용하도록 허용
        registry.enableStompBrokerRelay("/exchange")
                .setRelayHost(RELAY_HOST)
                .setRelayPort(RELAY_PORT)
                .setClientLogin(RELAY_USERNAME)
                .setClientPasscode(RELAY_PASSWORD)
                .setVirtualHost(VIRTUAL_HOST);
        //메시지 발행 url
        registry.setPathMatcher(new AntPathMatcher("."));
        registry.setApplicationDestinationPrefixes("/pub");
    }
}