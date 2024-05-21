package com.newcord.articleservice.domain.comments.service;

import com.newcord.articleservice.domain.comments.dto.CommentsResponse;

import java.util.List;

public interface CommentsQueryService {
    List<CommentsResponse.CommentsResponseDTO> getCommentsList(Long postID);
}
