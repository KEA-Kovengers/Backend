package com.newcord.articleservice.domain.editor.service;

import com.newcord.articleservice.domain.editor.entity.Editor;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostListResponseDTO;

public interface EditorQueryService {
    PostListResponseDTO getPostListByUserID(String userID, Integer page, Integer size);     //편집자 게시글 목록 조회
    Editor getEditorByPostIdAndUserID(Long postId, String userID);     //편집자 조회

}
