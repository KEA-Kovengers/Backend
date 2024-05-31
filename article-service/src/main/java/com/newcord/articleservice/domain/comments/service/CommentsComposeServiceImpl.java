package com.newcord.articleservice.domain.comments.service;

import com.newcord.articleservice.domain.comments.dto.CommentsResponse.*;
import com.newcord.articleservice.domain.comments.entity.Comments;
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

    @Override
    public List<CommentsUserResponseDTO> getUserCommentesList(Long userid){
        List<Comments> comments=commentsQueryService.getCommentUserid(userid);
        List<CommentsUserResponseDTO> result=new ArrayList<>();

        for(Comments comments1:comments){
            CommentsUserResponseDTO commentsUserResponseDTO=CommentsUserResponseDTO.builder()
                    .comments(comments1)
                    .posts(postsQueryService.getPost(comments1.getPostId()))
                    .build();
            result.add(commentsUserResponseDTO);
        }
        return result;
    }
}
