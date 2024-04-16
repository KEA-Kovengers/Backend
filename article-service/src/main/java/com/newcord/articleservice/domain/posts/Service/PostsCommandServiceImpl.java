package com.newcord.articleservice.domain.posts.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostsCommandServiceImpl implements PostsCommandService{
    /*
    TODO: 게시글 편집 세션(Exchange 생성 삭제등 관리에 관한 서비스 구현)
     */

}
