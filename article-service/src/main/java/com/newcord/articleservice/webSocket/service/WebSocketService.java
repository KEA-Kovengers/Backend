package com.newcord.articleservice.webSocket.service;

public interface WebSocketService {
    public void broadcastArticleEditMessage(String articleId, String message);

}
