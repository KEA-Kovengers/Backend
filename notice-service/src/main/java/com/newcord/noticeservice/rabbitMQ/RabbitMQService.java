package com.newcord.noticeservice.rabbitMQ;

public interface RabbitMQService {

    String createDirectExchange(String topicName);        //fanout exchange 생성

    String deleteTopic(String topicName);                //topic 삭제

    void createExchangeAndQueue(String topicName, String queueName);     //exchange와 queue 생성

    void sendMessage(String topicName, String routing, Object message);     //메시지 전송
}