package com.newcord.articleservice.domain.editor.service;

import com.newcord.articleservice.domain.editor.dto.EditorRequest.DeleteEditorRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorRequest.EditorAddRequestDTO;
import com.newcord.articleservice.domain.editor.entity.Editor;
import com.newcord.articleservice.domain.editor.repository.EditorRepository;
import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditorCommandServiceImpl implements EditorCommandService {
    private final EditorRepository editorRepository;

    @Override
    public Editor addEditor(Posts post, EditorAddRequestDTO editorAddDTO) {
        Editor editor = Editor.builder()
            .post(post)
            .userID(editorAddDTO.getUserID())
            .build();
        editorRepository.save(editor);

        return editor;
    }

    @Override
    public Editor deleteEditor(DeleteEditorRequestDTO deleteEditorRequestDTO) {
        // 삭제할 편집자 조회
        Editor deleteEditor = editorRepository.findByPostIdAndUserID(deleteEditorRequestDTO.getPostId(), deleteEditorRequestDTO.getUserID())
            .orElseThrow(() -> new ApiException(ErrorStatus._EDITOR_NOT_FOUND));

        editorRepository.delete(deleteEditor);

        return deleteEditor;
    }
}
