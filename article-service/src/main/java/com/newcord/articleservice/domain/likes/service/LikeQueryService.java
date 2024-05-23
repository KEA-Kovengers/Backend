package com.newcord.articleservice.domain.likes.service;

import com.newcord.articleservice.domain.likes.entity.Likes;

import java.util.List;

public interface LikeQueryService {
    List<Likes> getLikeList(Long userid);
}
