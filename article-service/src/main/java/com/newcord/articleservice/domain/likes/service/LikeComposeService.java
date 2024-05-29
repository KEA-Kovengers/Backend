package com.newcord.articleservice.domain.likes.service;

import com.newcord.articleservice.domain.likes.dto.LikeResponse;

import java.util.List;

public interface LikeComposeService {
    List<LikeResponse.LikeResponseDTO> getLikeList(Long userid);
}
