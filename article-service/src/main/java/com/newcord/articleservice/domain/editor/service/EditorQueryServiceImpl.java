package com.newcord.articleservice.domain.editor.service;

import com.newcord.articleservice.domain.editor.dto.EditorResponse.EditorListResponseDTO;
import com.newcord.articleservice.domain.editor.entity.Editor;
import com.newcord.articleservice.domain.editor.repository.EditorRepository;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostListResponseDTO;
import com.newcord.articleservice.domain.posts.enums.PostStatus;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EditorQueryServiceImpl implements EditorQueryService{
    private final EditorRepository editorRepository;

    @Override
    public PostListResponseDTO getPostListByUserID(Long userID, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Editor> editors = editorRepository.findByUserID(userID, pageRequest);
        List<Editor> filteredEditors = editors.getContent().stream()
                .filter(editor -> editor.getPost().getStatus() == PostStatus.POST)
                .collect(Collectors.toList());

        Page<Editor> filteredEditorsPage = new PageImpl<>(filteredEditors, pageRequest, filteredEditors.size());


        return PostListResponseDTO.builder()
                .postList(filteredEditorsPage)
                .build();
    }

    @Override
    public Editor getEditorByPostIdAndUserID(Long postId, Long userID) {
        Editor editor = editorRepository.findByPostIdAndUserID(postId, userID).orElse(null);
        if(editor == null){
            throw new ApiException(ErrorStatus._EDITOR_NOT_FOUND);
        }
        return editor;
    }

    @Override
    public EditorListResponseDTO getAllEditorsByPostId(Long postId) {
        return EditorListResponseDTO.builder()
            .postId(postId)
            .userID(editorRepository.findByPostId(postId).stream().map(Editor::getUserID).toList())
            .build();
    }
}
