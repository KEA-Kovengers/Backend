package com.newcord.articleservice.domain.editor.service;

import com.newcord.articleservice.domain.editor.dto.EditorRequest.DeleteEditorRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorRequest.EditorAddRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.DeleteEditorResponseDTO;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.EditorAddResponseDTO;

public interface EditorCommandService {
    EditorAddResponseDTO addEditor(String userID, EditorAddRequestDTO editorAddDTO);       //편집자 추가
    EditorAddResponseDTO addInitialEditor(EditorAddRequestDTO editorAddDTO); //초기 편집자 추가 (게시글 생성시 사용, 요청자 검증 없음)
    DeleteEditorResponseDTO deleteEditor(String userID, DeleteEditorRequestDTO deleteEditorRequestDTO);       //편집자 삭제
}
