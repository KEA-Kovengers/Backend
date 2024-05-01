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

    // 메시지 리스너 컨테이너 생성
    // Exchage에 바인딩된 Queue를 생성하고, 해당 Queue에 메시지 리스너를 등록하여 메세지를 consume한다.
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
//        container.setMessageListener(message -> {
//            // 이 부분의 순환참조를 해결해야한다.
//            // 현재 아이디어는 그냥 interceptor를 사용하지 않고, Client가 웹소켓은 그냥 서버 구독에 사용하고, 서버가 RabbitMQ를 구독하려면 따로 요청하도록
//            // 근데 레퍼런스 보면 Di~~~를 Bean으로 따로 생성하던데 찾아봐야할거같다.
//
//                   });

        // 필요에 따라 컨테이너 시작 및 관리
        container.start();

        return container;
    }

}
