package com.newcord.articleservice.config;

import com.newcord.articleservice.webSocket.interceptor.ArticleEditInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
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

    @Autowired
    private ArticleEditInterceptor articleEdditInterceptor;

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
                .setClientPasscode(RELAY_PASSWORD);
        // 클라이언트가 메시지를 보낼 때 사용할 prefix 설정 (/app -> @MessageMapping에 자동으로 매핑됨)
        registry.setApplicationDestinationPrefixes("/app");
    }

    // 인바운드 채널에 인터셉터 추가
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(articleEdditInterceptor);
    }
}