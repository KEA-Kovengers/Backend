package com.newcord.articleservice.domain.posts.Service;

import com.newcord.articleservice.domain.editor.dto.EditorRequest.EditorAddRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostCreateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostUpdateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostCreateResponseDTO;
import com.newcord.articleservice.domain.posts.entity.Posts;

public class PostsComposeServiceImpl implements PostsComposeService{

    @Override
    public PostCreateResponseDTO createPost(String userID, PostCreateRequestDTO postCreateDTO) {

//        editorCommandService.addInitialEditor(EditorAddRequestDTO.builder()
//            .postId(newPosts.getId())
//            .userID(userID)
//            .build());
//
//
//        articlesCommandService.createArticle(newPosts.getId());
//
//        return PostCreateResponseDTO.builder()
//            .id(newPosts.getId())
//            .build();
        return null;
    }

    @Override
    public Posts updatePost(String userID, PostUpdateRequestDTO postUpdateDTO) {
        // 요청 유저의 권한 확인
//        editorQueryService.getEditorByPostIdAndUserID(postUpdateDTO.getId(), userID);
        return null;
    }

    @Override
    public void deletePost(Long postId) {
//        articlesCommandService.deleteArticle(postId);
    }

    @Override
    public String createPostEditSession(String articleID) {
//        rabbitMQService.createFanoutExchange(articleID);
        return null;
    }

    @Override
    public String deletePostEditSession(String articleID) {
//        rabbitMQService.deleteTopic(articleID);
        return null;
    }
}
