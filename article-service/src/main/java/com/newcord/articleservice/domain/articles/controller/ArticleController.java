package com.newcord.articleservice.domain.articles.controller;

import com.newcord.articleservice.domain.articles.dto.ArticleRequest.BlockSequenceUpdateRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.BlockSequenceUpdateResponseDTO;
import com.newcord.articleservice.domain.articles.service.ArticlesCommandService;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockContentUpdateRequestDTO;
import com.newcord.articleservice.global.common.WSRequest;
import com.newcord.articleservice.global.common.response.ApiResponse;
import com.newcord.articleservice.global.common.response.WSResponse;
import com.newcord.articleservice.rabbitMQ.Service.RabbitMQService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
    private final ArticlesCommandService articlesCommandService;
    private final RabbitMQService rabbitMQService;

    @MessageMapping("/updateBlockSequence/{postID}")
    public WSResponse<BlockSequenceUpdateResponseDTO> updateBlockSequence(WSRequest<BlockSequenceUpdateRequestDTO> requestDTO, @DestinationVariable Long postID) {
        BlockSequenceUpdateResponseDTO responseDTO = articlesCommandService.updateBlockSequence(postID,requestDTO.getDto());
        WSResponse<BlockSequenceUpdateResponseDTO> response = WSResponse.onSuccess("/updateBlockSequence/"+postID, requestDTO.getUuid(), responseDTO);
        rabbitMQService.sendMessage(postID.toString(), "", response);

        return response;
    }

}
