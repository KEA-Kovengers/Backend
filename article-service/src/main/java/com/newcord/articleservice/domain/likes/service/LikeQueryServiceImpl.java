package com.newcord.articleservice.domain.likes.service;

import com.newcord.articleservice.domain.likes.entity.Likes;
import com.newcord.articleservice.domain.likes.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeQueryServiceImpl implements LikeQueryService{
    private final LikeRepository likeRepository;

    @Override
    public List<Likes> getLikeList(Long userid){
        return likeRepository.findAllByUser_id(userid);
    }
}
