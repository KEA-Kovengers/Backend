package com.newcord.articleservice.rabbitMQ.Service;

import com.newcord.articleservice.webSocket.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQServiceImpl implements RabbitMQService{
    @Value("${POD_NAME:default-pod}")   //쿠버네티스 Pod 이름, 기본값은 default-pod. Queue 식별에 사용
    private String podName;

    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private ConnectionFactory connectionFactory;


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

    @Override
    public void createExchangeAndQueue(String topicName, String queueName) {
        Exchange exchange = new FanoutExchange(topicName);
        Queue queue = new Queue(queueName, false);
        Binding binding = BindingBuilder.bind(queue).to(exchange).with("").noargs();

        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);
    }

}
