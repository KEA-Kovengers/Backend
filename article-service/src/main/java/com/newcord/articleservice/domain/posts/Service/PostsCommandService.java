package com.newcord.articleservice.domain.posts.Service;

import com.newcord.articleservice.domain.posts.dto.PostRequest.PostCreateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostCreateResponseDTO;

public interface PostsCommandService {

    PostCreateResponseDTO createPost(String userID, PostCreateRequestDTO postCreateDTO);        //게시글 생성


}
