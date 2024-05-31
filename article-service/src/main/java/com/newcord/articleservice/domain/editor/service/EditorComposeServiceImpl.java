package com.newcord.articleservice.domain.editor.service;

import com.newcord.articleservice.domain.article_version.entity.ArticleVersion;
import com.newcord.articleservice.domain.article_version.entity.Version;
import com.newcord.articleservice.domain.article_version.entity.VersionOperation;
import com.newcord.articleservice.domain.article_version.service.ArticleVersionQueryService;
import com.newcord.articleservice.domain.articles.entity.Article;
import com.newcord.articleservice.domain.articles.service.ArticlesCommandService;
import com.newcord.articleservice.domain.articles.service.ArticlesQueryService;
import com.newcord.articleservice.domain.block.service.BlockCommandService;
import com.newcord.articleservice.domain.editor.dto.EditorRequest.DeleteEditorRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorRequest.EditorAddRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.*;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.DeleteEditorResponseDTO;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.EditorAddResponseDTO;
import com.newcord.articleservice.domain.editor.entity.Editor;
import com.newcord.articleservice.domain.editor.repository.EditorRepository;
import com.newcord.articleservice.domain.log.dto.LogResponse.*;
import com.newcord.articleservice.domain.posts.Service.PostsCommandService;
import com.newcord.articleservice.domain.posts.Service.PostsQueryService;
import com.newcord.articleservice.domain.posts.entity.Posts;

import com.newcord.articleservice.domain.posts.enums.PostStatus;
import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EditorComposeServiceImpl implements EditorComposeService{
    private final EditorCommandService editorCommandService;
    private final EditorQueryService editorQueryService;
    private final PostsCommandService postsCommandService;
    private final PostsQueryService postsQueryService;
    private final ArticlesCommandService articlesCommandService;
    private final ArticlesQueryService articlesQueryService;
    private final BlockCommandService blockCommandService;


    private final EditorRepository editorRepository;

    @Override
    public EditorAddResponseDTO addEditor(Long userID, EditorAddRequestDTO editorAddDTO) {
        // 권한 확인
        editorQueryService.getEditorByPostIdAndUserID(editorAddDTO.getPostId(), userID);

        // 게시글 조회
        Posts posts = postsQueryService.getPost(editorAddDTO.getPostId());

        // 에디터 추가
        Editor newEditor = editorCommandService.addEditor(posts, editorAddDTO);

        return EditorAddResponseDTO.builder()
            .postId(newEditor.getPost().getId())
            .userID(newEditor.getUserID())
            .build();
    }

    @Override
    public DeleteEditorResponseDTO deleteEditor(Long userID,
        DeleteEditorRequestDTO deleteEditorRequestDTO) {
        // 권한 확인
        editorQueryService.getEditorByPostIdAndUserID(deleteEditorRequestDTO.getPostId(), userID);

        // 에디터 삭제
        Editor deleteEditor = editorCommandService.deleteEditor(deleteEditorRequestDTO);

        DeleteEditorResponseDTO deleteEditorResponseDTO = DeleteEditorResponseDTO.builder()
            .postId(deleteEditor.getPost().getId())
            .userID(deleteEditor.getUserID())
            .build();

        // 게시글에 대한 에디터 리스트 조회
        // 에디터가 존재하지 않으면 게시글 완전 삭제
        // input DB 조회 (CDC오차를 고려하여 output DB가 아닌 input DB를 조회함)
        List<Editor> editorList = editorRepository.findByPostId(deleteEditorRequestDTO.getPostId());
        if(editorList.isEmpty()) {
            deleteEditorResponseDTO.setPostDelete(true);
        }

        // 관련 엔티티 삭제
        if(deleteEditorResponseDTO.isPostDelete()) {
            Article aritcle = articlesCommandService.deleteArticle(deleteEditorRequestDTO.getPostId());
            for (String blockId : aritcle.getBlock_list()) {
                blockCommandService.deleteBlock(blockId);
            }
            postsCommandService.deletePost(deleteEditorRequestDTO.getPostId());
        }

        return deleteEditorResponseDTO;
    }

    @Override
    public DraftsResponseDTO getDraftList(Long userID, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Editor> editorList = editorRepository.findByUserID(userID, PostStatus.EDIT, pageable);
        return DraftsResponseDTO.builder()
            .drafts(editorList.getContent().stream().map(editor -> DraftEntity.builder()
                .postId(editor.getPost().getId())
                .title(editor.getPost().getTitle())
                .updatedAt(editor.getUpdated_at())
                .build()).toList())
            .build();
    }


}

