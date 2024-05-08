package com.newcord.articleservice.domain.posts.Service;

import com.newcord.articleservice.domain.posts.dto.PostRequest.PostCreateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostUpdateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostCreateResponseDTO;
import com.newcord.articleservice.domain.posts.entity.Posts;

public interface PostsCommandService {

    PostCreateResponseDTO createPost(String userID, PostCreateRequestDTO postCreateDTO);        //게시글 생성
    Posts updatePost(String userID, PostUpdateRequestDTO postUpdateDTO);        //게시글 수정
    void deletePost(String userID, Long postId);        //게시글 삭제
    String createPostEditSession(String articleID);        //게시글 편집 세션 생성
    String deletePostEditSession(String articleID);        //게시글 편집 세션 삭제
}
