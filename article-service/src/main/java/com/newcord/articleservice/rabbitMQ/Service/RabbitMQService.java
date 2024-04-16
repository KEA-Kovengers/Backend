package com.newcord.articleservice.rabbitMQ.Service;

public interface RabbitMQService {
    public String createTopic(String topicName);
    public String deleteTopic(String topicName);
}
