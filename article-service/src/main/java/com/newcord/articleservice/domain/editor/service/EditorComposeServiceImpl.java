package com.newcord.articleservice.domain.editor.service;

import com.newcord.articleservice.domain.editor.dto.EditorRequest.DeleteEditorRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorRequest.EditorAddRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.DeleteEditorResponseDTO;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.EditorAddResponseDTO;
import com.newcord.articleservice.domain.editor.entity.Editor;
import java.util.List;

public class EditorComposeServiceImpl implements EditorComposeService{

    @Override
    public EditorAddResponseDTO addEditor(String userID, EditorAddRequestDTO editorAddDTO) {
        return null;
    }

    @Override
    public EditorAddResponseDTO addInitialEditor(EditorAddRequestDTO editorAddDTO) {
        return null;
    }

    @Override
    public DeleteEditorResponseDTO deleteEditor(String userID,
        DeleteEditorRequestDTO deleteEditorRequestDTO) {
        // 에디터 삭제

        // 게시글에 대한 에디터 리스트 조회

        // 에디터가 존재하지 않으면 게시글 완전 삭제

        // input DB 조회 (CDC오차를 고려하여 output DB가 아닌 input DB를 조회함
//        List<Editor> editorList = editorRepository.findByPostId(deleteEditorRequestDTO.getPostId());
//        if(editorList.isEmpty()) {
//            deleteEditorResponseDTO.setPostDelete(true);
//        }



        return null;
    }
}
