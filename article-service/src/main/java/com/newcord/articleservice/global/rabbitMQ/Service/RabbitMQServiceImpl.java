package com.newcord.articleservice.global.rabbitMQ.Service;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQServiceImpl implements RabbitMQService{
    @Value("${POD_NAME:default-pod}")   //쿠버네티스 Pod 이름, 기본값은 default-pod. Queue 식별에 사용
    private String podName;

    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public String createFanoutExchange(String topicName) {
        FanoutExchange exchange = ExchangeBuilder.fanoutExchange(topicName)
            .autoDelete()
            .durable(true)
            .build();
        rabbitAdmin.declareExchange(exchange);
        return "Topic created " + topicName;
    }

    @Override
    public String deleteTopic(String topicName) {
        rabbitAdmin.deleteExchange(topicName);
        return "Topic deleted " + topicName;
    }

    @Override
    public void createExchangeAndQueue(String topicName, String queueName) {
        Exchange exchange = ExchangeBuilder.fanoutExchange(topicName)
            .autoDelete()
            .durable(true)
            .build();
        Queue queue = new Queue(queueName, false);
        Binding binding = BindingBuilder.bind(queue).to(exchange).with("").noargs();

        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);
    }

    @Override
    public void sendMessage(String topicName, String routing, Object message) {
        rabbitTemplate.convertAndSend(topicName, routing, message);
    }


}
