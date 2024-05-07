package com.newcord.articleservice.domain.posts.Service;

import com.newcord.articleservice.domain.posts.dto.PostRequest.PostCreateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostUpdateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostCreateResponseDTO;
import com.newcord.articleservice.domain.posts.entity.Posts;

public interface PostsCommandService {

    PostCreateResponseDTO createPost(String userID, PostCreateRequestDTO postCreateDTO);        //게시글 생성
    Posts updatePost(String userID, PostUpdateRequestDTO postUpdateDTO);        //게시글 수정


}
