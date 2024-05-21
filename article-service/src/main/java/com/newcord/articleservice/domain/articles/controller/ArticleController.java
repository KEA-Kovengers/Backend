package com.newcord.articleservice.domain.articles.controller;

import com.newcord.articleservice.domain.articles.dto.ArticleRequest.BlockSequenceUpdateRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.BlockSequenceUpdateResponseDTO;
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
        BlockSequenceUpdateResponseDTO responseDTO = articleComposeService.updateBlockSequence("testID", postID, requestDTO.getDto());
        WSResponse<BlockSequenceUpdateResponseDTO> response = WSResponse.onSuccess("/updateBlockSequence/"+postID, requestDTO.getUuid(), responseDTO);
        rabbitMQService.sendMessage(postID.toString(), "", response);

        return response;
    }

}
