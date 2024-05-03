package com.newcord.articleservice.domain.editor.editor;

import com.newcord.articleservice.domain.editor.dto.EditorRequest.EditorAddRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.EditorAddResponseDTO;

public interface EditorCommandService {

    EditorAddResponseDTO addEditor(EditorAddRequestDTO editorAddDTO);       //편집자 추가

}
