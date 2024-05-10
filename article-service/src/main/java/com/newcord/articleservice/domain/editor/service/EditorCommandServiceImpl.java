package com.newcord.articleservice.domain.editor.service;

import com.newcord.articleservice.domain.editor.dto.EditorRequest.DeleteEditorRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorRequest.EditorAddRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.DeleteEditorResponseDTO;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.EditorAddResponseDTO;
import com.newcord.articleservice.domain.editor.entity.Editor;
import com.newcord.articleservice.domain.editor.repository.EditorRepository;
import com.newcord.articleservice.domain.posts.Service.PostsCommandService;
import com.newcord.articleservice.domain.posts.Service.PostsQueryService;
import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditorCommandServiceImpl implements EditorCommandService {
    private final EditorRepository editorRepository;

    @Override
    public Editor addEditor(String userID, Posts post, EditorAddRequestDTO editorAddDTO) {
        // 요청 유저의 권한 확인
        editorRepository.findByPostIdAndUserID(editorAddDTO.getPostId(), userID)
            .ifPresent(a -> {
                throw new ApiException(ErrorStatus._EDITOR_NOT_FOUND);
            });

        return addInitialEditor(post, editorAddDTO);
    }

    @Override
    public Editor addInitialEditor(Posts post, EditorAddRequestDTO editorAddDTO) {
        Editor editor = Editor.builder()
            .post(post)
            .userID(editorAddDTO.getUserID())
            .build();
        editorRepository.save(editor);

        return editor;
    }

    @Override
    public Editor deleteEditor(String userID, DeleteEditorRequestDTO deleteEditorRequestDTO) {
        // 요청한 유저의 권한 확인
        editorRepository.findByPostIdAndUserID(deleteEditorRequestDTO.getPostId(), userID)
            .ifPresent(a -> {
                throw new ApiException(ErrorStatus._EDITOR_NOT_FOUND);
            });

        // 삭제할 편집자 조회
        Editor deleteEditor = editorRepository.findByPostIdAndUserID(deleteEditorRequestDTO.getPostId(), deleteEditorRequestDTO.getUserID())
            .orElseThrow(() -> new ApiException(ErrorStatus._EDITOR_NOT_FOUND));

        editorRepository.delete(deleteEditor);
        
        return deleteEditor;
    }
}
