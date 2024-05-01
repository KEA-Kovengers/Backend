package com.newcord.articleservice.webSocket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketServiceImpl implements WebSocketService{
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;        // WebSocket 메시지 전송을 위한 템플릿 (@SendTo 와 동일)

    @Override
    public void broadcastArticleEditMessage(String articleId, String message) {
        simpMessagingTemplate.convertAndSend("/topic/articleEditSession/" + articleId, message);
    }
}
