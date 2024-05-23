package com.newcord.articleservice.domain.comments.service;

import com.newcord.articleservice.domain.comments.dto.CommentsRequest.*;
import com.newcord.articleservice.domain.comments.dto.CommentsResponse;
import com.newcord.articleservice.domain.comments.entity.Comments;

public interface CommentsCommandService {
    Comments createComment(CommentsCreateRequestDTO commentsCreateRequestDTO);
    Comments deleteComment(CommentsRequestDTO commentsRequestDTO);
}
