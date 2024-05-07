package com.newcord.articleservice.domain.editor.service;

import com.newcord.articleservice.domain.editor.dto.EditorRequest.EditorAddRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.EditorAddResponseDTO;
import com.newcord.articleservice.domain.editor.entity.Editor;
import com.newcord.articleservice.domain.editor.repository.EditorRepository;
import com.newcord.articleservice.domain.posts.Service.PostsQueryService;
import com.newcord.articleservice.domain.posts.entity.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditorCommandServiceImpl implements EditorCommandService {
    private final EditorRepository editorRepository;
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
}
