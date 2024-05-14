package com.newcord.articleservice.domain.editor.service;

import com.newcord.articleservice.domain.editor.dto.EditorRequest.DeleteEditorRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorRequest.EditorAddRequestDTO;
import com.newcord.articleservice.domain.editor.entity.Editor;
import com.newcord.articleservice.domain.posts.entity.Posts;

public interface EditorCommandService {
    Editor addEditor(Posts post,EditorAddRequestDTO editorAddDTO); //초기 편집자 추가 (게시글 생성시 사용, 요청자 검증 없음)
    Editor deleteEditor(DeleteEditorRequestDTO deleteEditorRequestDTO);       //편집자 삭제
}
