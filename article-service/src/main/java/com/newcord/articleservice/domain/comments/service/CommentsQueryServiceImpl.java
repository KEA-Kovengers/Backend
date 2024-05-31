package com.newcord.articleservice.domain.comments.service;

import com.newcord.articleservice.domain.comments.entity.Comments;
import com.newcord.articleservice.domain.comments.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentsQueryServiceImpl implements CommentsQueryService{

    private final CommentsRepository commentsRepository;

    @Override
    public List<Comments> getCommentsList(Long postID){
        List<Comments> comments = commentsRepository.findByPostId(postID);
        comments.sort(Comparator.comparing(Comments::getComment_id));
        return comments;
    }

    @Override
    public List<Comments> getCommentUserid(Long userid){
        return commentsRepository.findByUser_id(userid);
    }
}
