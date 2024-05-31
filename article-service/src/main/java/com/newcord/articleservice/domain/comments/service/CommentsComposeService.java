package com.newcord.articleservice.domain.comments.service;

import com.newcord.articleservice.domain.comments.dto.CommentsResponse;

import java.util.List;

public interface CommentsComposeService {
    List<CommentsResponse.CommentsUserResponseDTO> getUserCommentesList(Long userid);
}
