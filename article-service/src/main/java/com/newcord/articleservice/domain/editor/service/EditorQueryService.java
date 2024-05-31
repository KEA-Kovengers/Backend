package com.newcord.articleservice.domain.editor.service;

import com.newcord.articleservice.domain.editor.dto.EditorResponse.*;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.EditorListResponseDTO;
import com.newcord.articleservice.domain.editor.entity.Editor;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostListResponseDTO;
import java.util.List;

public interface EditorQueryService {
    PostListResponseDTO getPostListByUserID(Long userID, Integer page, Integer size);     //편집자 게시글 목록 조회
    Editor getEditorByPostIdAndUserID(Long postId, Long userID);     //편집자 조회
    EditorListResponseDTO getAllEditorsByPostId(Long postId);     //게시글의 모든 편집자 조회
}
