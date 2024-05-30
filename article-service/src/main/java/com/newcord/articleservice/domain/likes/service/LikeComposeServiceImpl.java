package com.newcord.articleservice.domain.likes.service;

import com.newcord.articleservice.domain.likes.dto.LikeResponse.*;
import com.newcord.articleservice.domain.likes.entity.Likes;
import com.newcord.articleservice.domain.posts.Service.PostsQueryService;
import com.newcord.articleservice.domain.posts.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeComposeServiceImpl implements LikeComposeService{

    private final LikeQueryService likeQueryService;
    private final PostsQueryService postsQueryService;

    @Override
    public List<LikeResponseDTO> getLikeList(Long userid){
        List<Likes> likes=likeQueryService.getLikeList(userid);
        List<LikeResponseDTO> result=new ArrayList<>();
        for(Likes likes1:likes){
            LikeResponseDTO likeResponseDTO=LikeResponseDTO.builder()
                    .likes(likes1)
                    .posts(postsQueryService.getPost(likes1.getPost_id()))
                    .build();
            result.add(likeResponseDTO);
        }
        return result;
    }
}
