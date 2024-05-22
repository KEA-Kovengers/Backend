package com.newcord.articleservice.domain.likes.service;

import com.newcord.articleservice.domain.likes.dto.LikeRequest.*;
import com.newcord.articleservice.domain.likes.entity.Likes;
import com.newcord.articleservice.domain.likes.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LikeCommandServiceImpl implements LikeCommandService{

    private final LikeRepository likeRepository;

    //좋아요
    @Override
    public Likes createLike(CreateLikeRequestDTO createLikeRequestDTO){
        Likes likes=Likes.builder()
                .user_id(createLikeRequestDTO.getUser_id())
                .post_id(createLikeRequestDTO.getPost_id())
                .build();
        likeRepository.save(likes);
        return likes;
    }

    //좋아요 취소
    @Override
    public String deleteLike(Long id){
        likeRepository.deleteById(id);
        return "좋아요가 취소되었습니다";
    }
}
