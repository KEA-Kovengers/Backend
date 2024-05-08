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
    private final EditorQueryService editorQueryService;
    private final PostsQueryService postsQueryService;

    @Override
    public EditorAddResponseDTO addEditor(EditorAddRequestDTO editorAddDTO) {
        Posts post = postsQueryService.getPost(editorAddDTO.getPostId());
        Editor editor = Editor.builder()
                .post(post)
                .userID(editorAddDTO.getUserID())
                .build();
        editorRepository.save(editor);

        return EditorAddResponseDTO.builder()
                .userID(editor.getUserID())
                .postId(editor.getPost().getId())
                .build();
    }

    @Override
    public DeleteEditorResponseDTO deleteEditor(String userID, DeleteEditorRequestDTO deleteEditorRequestDTO) {
        Editor editor = editorQueryService.getEditorByPostIdAndUserID(deleteEditorRequestDTO.getPostId(), userID);
        Editor deleteEditor = editorQueryService.getEditorByPostIdAndUserID(deleteEditorRequestDTO.getPostId(), deleteEditorRequestDTO.getUserID());

        editorRepository.delete(deleteEditor);

        DeleteEditorResponseDTO deleteEditorResponseDTO = DeleteEditorResponseDTO.builder()
                .userID(deleteEditor.getUserID())
                .postId(deleteEditor.getPost().getId())
                .build();

        List<Editor> editorList = editorRepository.findByPostId(deleteEditorRequestDTO.getPostId());
        if(editorList.isEmpty()) {
            deleteEditorResponseDTO.setPostDelete(true);
        }

        return deleteEditorResponseDTO;
    }
}
