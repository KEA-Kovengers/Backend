package com.newcord.articleservice.rabbitMQ.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQServiceImpl implements RabbitMQService{
    @Value("${POD_NAME:default-pod}")   //쿠버네티스 Pod 이름, 기본값은 default-pod. Queue 식별에 사용
    private String podName;

    @Autowired
    private final RabbitAdmin rabbitAdmin;
    @Autowired
    private final ConnectionFactory connectionFactory;
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

    // 메시지 리스너 컨테이너 생성
    // podName을 Queue 이름에 사용하여 각 Pod 별로 Queue를 생성 (서비스간 메시지 전달에 사용)
    @Override
    public SimpleMessageListenerContainer createContainer(String topicName) {
        String queueName = topicName + "-" + podName;
        String routingKey = "post." + topicName;

        Exchange exchange = new FanoutExchange(topicName);
        Queue queue = new Queue(queueName, false);
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();

        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.addQueues(queue);
        container.setMessageListener(message -> {
            // 메시지 처리 로직
            System.out.println("Received message: " + new String(message.getBody()));
        });

        // 필요에 따라 컨테이너 시작 및 관리
        container.start();

        return container;
    }
}
