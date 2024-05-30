package com.newcord.articleservice.domain.likes.service;

import com.newcord.articleservice.domain.likes.dto.LikeRequest.*;
import com.newcord.articleservice.domain.likes.entity.Likes;

public interface LikeCommandService {
    Likes createLike(Long userID,CreateLikeRequestDTO createLikeRequestDTO);

    //좋아요 취소
    String deleteLike(Long id);
}
