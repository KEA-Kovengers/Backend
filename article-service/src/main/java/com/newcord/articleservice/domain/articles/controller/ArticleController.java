package com.newcord.articleservice.domain.articles.controller;

import com.newcord.articleservice.domain.articles.dto.ArticleRequest.BlockSequenceUpdateRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleRequest.TitleUpdateRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.BlockSequenceUpdateResponseDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.TitleUpdateResponseDTO;
import com.newcord.articleservice.domain.articles.service.ArticleComposeService;
import com.newcord.articleservice.global.common.WSRequest;
import com.newcord.articleservice.global.common.response.WSResponse;
import com.newcord.articleservice.global.rabbitMQ.Service.RabbitMQService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
    private final ArticleComposeService articleComposeService;
    private final RabbitMQService rabbitMQService;

    @MessageMapping("/updateBlockSequence/{postID}")
    public WSResponse<BlockSequenceUpdateResponseDTO> updateBlockSequence(WSRequest<BlockSequenceUpdateRequestDTO> requestDTO, @DestinationVariable Long postID) {
        BlockSequenceUpdateResponseDTO responseDTO = articleComposeService.updateBlockSequence(requestDTO.getUserID(), postID, requestDTO.getDto());
        WSResponse<BlockSequenceUpdateResponseDTO> response = WSResponse.onSuccess("/updateBlockSequence/"+postID, requestDTO.getUuid(), responseDTO);
        rabbitMQService.sendMessage(postID.toString(), "", response);

        return response;
    }

    //제목 수정 ws api
    @MessageMapping("/updateTitle/{postID}")
    public WSResponse<TitleUpdateResponseDTO> updateTitle(WSRequest<TitleUpdateRequestDTO> requestDTO, @DestinationVariable Long postID) {
        TitleUpdateResponseDTO responseDTO = articleComposeService.updateTitle(requestDTO.getUserID(), postID, requestDTO.getDto());
        WSResponse<TitleUpdateResponseDTO> response = WSResponse.onSuccess("/updateTitle/"+postID, requestDTO.getUuid(), responseDTO);
        rabbitMQService.sendMessage(postID.toString(), "", response);

        return response;
    }
    // 해시태그 수정 ws api

}
