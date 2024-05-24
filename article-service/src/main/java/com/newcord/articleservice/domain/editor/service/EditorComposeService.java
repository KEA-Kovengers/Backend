package com.newcord.articleservice.domain.editor.service;

import com.newcord.articleservice.domain.editor.dto.EditorRequest.DeleteEditorRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorRequest.EditorAddRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.*;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.DeleteEditorResponseDTO;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.EditorAddResponseDTO;

import java.util.List;

public interface EditorComposeService {
    EditorAddResponseDTO addEditor(Long userID, EditorAddRequestDTO editorAddDTO);       //편집자 추가
    DeleteEditorResponseDTO deleteEditor(Long userID, DeleteEditorRequestDTO deleteEditorRequestDTO);       //편집자 삭제

    //    @Override
    //    public EditorResponse.EditorLogResponseDTO getEditorsLogData(Long postID) {
    //        EditorResponse.EditorListResponseDTO editorListResponseDTO=editorQueryService.getAllEditorsByPostId(postID);
    //        List<Long> userid= editorListResponseDTO.getUserID();
    //        int userIdsCount =userid.size();
    //        int cnt=0;
    //        Article article=articlesQueryService.findArticleById(postID);
    //        ArticleVersion articleVersion=articleVersionQueryService.findArticleVersionById(article.getId());
    //           // 각 버전의 operations 리스트에 접근
    //        for (Version version : articleVersion.getVersions()) {
    //            for (int j=0;j<userIdsCount;j++){
    //                if(version.getOperations()[cnt])
    //            }
    //        }
    //    }
    List<EditorLogResponseDTO> getEditorsLogData(Long postID);
}
