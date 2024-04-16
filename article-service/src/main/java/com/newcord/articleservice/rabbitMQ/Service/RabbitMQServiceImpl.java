package com.newcord.articleservice.rabbitMQ.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQServiceImpl implements RabbitMQService{
    @Autowired
    private final RabbitAdmin rabbitAdmin;
    @Override
    public String createTopic(String topicName) {
        FanoutExchange exchange = new FanoutExchange(topicName);
        rabbitAdmin.declareExchange(exchange);
        return "Topic created " + topicName;
    }

    @Override
    public String deleteTopic(String topicName) {
        rabbitAdmin.deleteExchange(topicName);
        return "Topic deleted " + topicName;
    }

}
