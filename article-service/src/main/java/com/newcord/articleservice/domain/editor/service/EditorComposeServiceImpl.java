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
import com.newcord.articleservice.domain.posts.Service.PostsCommandService;
import com.newcord.articleservice.domain.posts.Service.PostsQueryService;
import com.newcord.articleservice.domain.posts.entity.Posts;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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

    private final ArticleVersionQueryService articleVersionQueryService;
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
    public List<EditorLogResponseDTO> getEditorsLogData(Long postID) {
        EditorListResponseDTO editorListResponseDTO = editorQueryService.getAllEditorsByPostId(postID);
        List<Long> userIds = editorListResponseDTO.getUserID();
        int userIdsCount = userIds.size();
        List<EditorLogResponseDTO> result = new ArrayList<>();
        Article article = articlesQueryService.findArticleById(postID);
        ArticleVersion articleVersion = articleVersionQueryService.findArticleVersionById(article.getId());

        for (Version version : articleVersion.getVersions()) {
            List<VersionOperation> operations = version.getOperations();
            for (int i = 0; i < operations.size(); i++) {
                VersionOperation operation = operations.get(i);
                for (int j = 0; j < userIdsCount; j++) {
                    if (operation.getUpdated_by().getUpdater_id().equals(userIds.get(j))) {

                        EditorLogResponseDTO editorLogResponseDTO = EditorLogResponseDTO.builder()
                                .userID(userIds.get(j))
                                .blockId(operation.getId().toString())
                                .build();
                        result.add(editorLogResponseDTO);
                    }
                }
            }
        }

    return result;
    }

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


}

