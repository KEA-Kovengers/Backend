package com.newcord.articleservice.domain.articles.controller;

import com.newcord.articleservice.domain.articles.dto.ArticleRequest.BlockSequenceUpdateRequestDTO;
import com.newcord.articleservice.domain.articles.service.ArticlesCommandService;
import com.newcord.articleservice.domain.block.dto.BlockRequest.BlockContentUpdateRequestDTO;
import com.newcord.articleservice.global.common.WSRequest;
import com.newcord.articleservice.global.common.response.ApiResponse;
import com.newcord.articleservice.global.common.response.WSResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

// HTTP

@Controller
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
    private final ArticlesCommandService articlesCommandService;

//    @MessageMapping("/updateBlock/{postID}")
//    public WSResponse<String> updateBlock(WSRequest<BlockSequenceUpdateRequestDTO> requestDTO, @DestinationVariable Long postID) {
////        articlesCommandService.updateBlockSequence(blockSequenceUpdateDTO);
//        return WSResponse.onSuccess("/updateBlock/"+postID,requestDTO.getUuid(),"Block Sequence updated");
//    }

}
