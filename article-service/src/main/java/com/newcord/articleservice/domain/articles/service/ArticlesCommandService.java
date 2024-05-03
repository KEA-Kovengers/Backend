package com.newcord.articleservice.domain.articles.service;

import com.newcord.articleservice.domain.articles.dto.ArticleRequest.ArticleCreateRequestDTO;
import com.newcord.articleservice.domain.articles.dto.ArticleResponse.ArticleCreateResponseDTO;

public interface ArticlesCommandService {
    ArticleCreateResponseDTO createArticle(ArticleCreateRequestDTO articleCreateRequestDTO);


}
