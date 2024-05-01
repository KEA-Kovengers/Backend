package com.newcord.articleservice.config;

import com.newcord.articleservice.rabbitMQ.Service.RabbitMQService;
import com.newcord.articleservice.rabbitMQ.Service.RabbitMQServiceImpl;
import com.newcord.articleservice.webSocket.interceptor.ArticleEditInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@Configuration
public class MessageConfig {
//    @Bean
//    public ArticleEditInterceptor articleEditInterceptor() {
//        return new ArticleEditInterceptor();
//    }
}
