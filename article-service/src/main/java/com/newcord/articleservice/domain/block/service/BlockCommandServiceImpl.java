package com.newcord.articleservice.domain.block.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockContentUpdateDTO;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.exception.ExceptionAdvice;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import com.newcord.articleservice.rabbitMQ.Service.RabbitMQService;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockCommandServiceImpl implements BlockCommandService{
    @Autowired
    private RabbitMQService rabbitMQService;
    @Override
    public JSONPObject updateBlock(BlockContentUpdateDTO blockContentUpdateDTO, String postId) {
        // 블록 업데이트 로직 후 ResponseDTO로 전송
        rabbitMQService.sendMessage(postId, "", blockContentUpdateDTO);
        return null;
    }
}
