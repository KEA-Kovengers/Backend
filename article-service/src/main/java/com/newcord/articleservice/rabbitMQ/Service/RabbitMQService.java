package com.newcord.articleservice.rabbitMQ.Service;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

public interface RabbitMQService {
    public String createTopic(String topicName);
    public String deleteTopic(String topicName);
    public SimpleMessageListenerContainer createContainer(String topicName);
}
