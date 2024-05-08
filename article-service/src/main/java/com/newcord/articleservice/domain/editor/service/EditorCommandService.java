package com.newcord.articleservice.domain.editor.service;

import com.newcord.articleservice.domain.editor.dto.EditorRequest.DeleteEditorRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorRequest.EditorAddRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.DeleteEditorResponseDTO;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.EditorAddResponseDTO;

public interface EditorCommandService {
    EditorAddResponseDTO addEditor(EditorAddRequestDTO editorAddDTO);       //편집자 추가
    DeleteEditorResponseDTO deleteEditor(String userID, DeleteEditorRequestDTO deleteEditorRequestDTO);       //편집자 삭제
}
