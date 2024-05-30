package com.newcord.articleservice.domain.editor.service;

import com.newcord.articleservice.domain.editor.dto.EditorRequest.DeleteEditorRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorRequest.EditorAddRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.*;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.DeleteEditorResponseDTO;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.EditorAddResponseDTO;
import com.newcord.articleservice.domain.log.dto.LogResponse;

import java.util.List;

public interface EditorComposeService {
    EditorAddResponseDTO addEditor(Long userID, EditorAddRequestDTO editorAddDTO);       //편집자 추가
    DeleteEditorResponseDTO deleteEditor(Long userID, DeleteEditorRequestDTO deleteEditorRequestDTO);       //편집자 삭제

}
