package com.newcord.articleservice.domain.comments.service;

import com.newcord.articleservice.domain.comments.dto.CommentsResponse.*;
import com.newcord.articleservice.domain.comments.entity.Comments;
import com.newcord.articleservice.domain.comments.repository.CommentsRepository;
import com.newcord.articleservice.domain.likes.repository.LikeRepository;
import com.newcord.articleservice.domain.posts.Service.PostsQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentsComposeServiceImpl implements CommentsComposeService{

    private final CommentsQueryService commentsQueryService;
    private final PostsQueryService postsQueryService;
    private final LikeRepository likeRepository;
    private final CommentsRepository commentsRepository;
    @Override
    public List<CommentsUserResponseDTO> getUserCommentesList(Long userid){
        List<Comments> comments=commentsQueryService.getCommentUserid(userid);
        List<CommentsUserResponseDTO> result=new ArrayList<>();

        for(Comments comments1:comments){
            CommentsUserResponseDTO commentsUserResponseDTO=CommentsUserResponseDTO.builder()
                    .comments(comments1)
                    .posts(postsQueryService.getPost(comments1.getPostId()))
                    .likeCnt(likeRepository.findAllByPost_id(comments1.getPostId()).size())
                    .commentCnt(commentsRepository.findByPostId(comments1.getPostId()).size())
                    .build();
            result.add(commentsUserResponseDTO);
        }
        return result;
    }
}
