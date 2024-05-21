package com.newcord.articleservice.domain.comments.service;

import com.newcord.articleservice.domain.comments.dto.CommentsRequest.*;
import com.newcord.articleservice.domain.comments.entity.Comments;
import com.newcord.articleservice.domain.comments.repository.CommentsRepository;
import com.newcord.articleservice.domain.posts.repository.PostsRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentsCommandServiceImpl implements CommentsCommandService{

    private final CommentsRepository commentsRepository;
    private final PostsRepository postsRepository;

    // 댓글 작성, 대댓글 작성
    // 댓글은 commentID가 null
    // 대댓글은 commentID에 부모 댓글의 id
    @Override
    public Comments createComment(CommentsCreateRequestDTO commentsCreateRequestDTO){
        Comments comments=Comments.builder().
                comment_id(commentsCreateRequestDTO.getCommentID())
                .post_id(commentsCreateRequestDTO.getPostID())
                .user_id(commentsCreateRequestDTO.getUserID())
                .isDeleted(false)
                .body((commentsCreateRequestDTO.getBody()))
        .build();
        log.info(String.valueOf(comments.getComment_id()));
        commentsRepository.save(comments);
        return comments;
    }

    @Override
    public Comments deleteComment(CommentsRequestDTO commentsRequestDTO){
        Comments comments= commentsRepository.findById(commentsRequestDTO.getId()).orElseThrow(()-> new ApiException(ErrorStatus._COMMENT_NOT_FOUND));
        comments.setDeleted(true);
        return comments;
    }

}
