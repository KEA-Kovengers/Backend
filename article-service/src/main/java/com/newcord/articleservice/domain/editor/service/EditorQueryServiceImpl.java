package com.newcord.articleservice.domain.editor.service;

import com.newcord.articleservice.domain.comments.repository.CommentsRepository;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.*;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.EditorListResponseDTO;
import com.newcord.articleservice.domain.editor.entity.Editor;
import com.newcord.articleservice.domain.editor.repository.EditorRepository;
import com.newcord.articleservice.domain.likes.repository.LikeRepository;
import com.newcord.articleservice.domain.posts.enums.PostStatus;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;

import java.util.ArrayList;
import java.util.List;



import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EditorQueryServiceImpl implements EditorQueryService{
    private final EditorRepository editorRepository;
    private final LikeRepository likeRepository;
    private final CommentsRepository commentsRepository;

    @Override
    public EditorPostListResponseDTO getPostListByUserID(Long userID, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<EditorPostResponseDTO> result=new ArrayList<>();
        Page<Editor> editors = editorRepository.findByUserID(userID,PostStatus.POST, pageRequest);

        for(Editor editor:editors){
           int likeCnt=likeRepository.findAllByPost_id(editor.getPost().getId()).size();
           int commentCnt=commentsRepository.findByPostId(editor.getPost().getId()).size();
            EditorPostResponseDTO editorPostResponseDTO=EditorPostResponseDTO.builder()
                    .postList(editor)
                    .likeCnt(likeCnt)
                    .commentCnt(commentCnt)
                    .build();
            result.add(editorPostResponseDTO);
        }
        return EditorPostListResponseDTO.builder()
                .editorPostResponseDTOS(result)
                .build();


    }
    @Override
    public Editor getEditorByPostIdAndUserID(Long postId, Long userID) {
        Editor editor = editorRepository.findByPostIdAndUserID(postId, userID).orElse(null);
        if(editor == null){
            throw new ApiException(ErrorStatus._EDITOR_NOT_FOUND);
        }
        return editor;
    }

    @Override
    public EditorListResponseDTO getAllEditorsByPostId(Long postId) {

        return EditorListResponseDTO.builder()
            .postId(postId)
            .userID(editorRepository.findAllByPostId(postId).stream().map(Editor::getUserID).toList())
            .build();
    }

}
