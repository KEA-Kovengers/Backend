package com.newcord.articleservice.domain.comments.service;

import com.newcord.articleservice.domain.comments.dto.CommentsResponse;
import com.newcord.articleservice.domain.comments.entity.Comments;

import java.util.List;

public interface CommentsQueryService {
    List<Comments> getCommentsList(Long postID);
}
