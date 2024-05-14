package com.newcord.articleservice.domain.posts.Service;

import com.newcord.articleservice.domain.posts.dto.PostRequest.PostCreateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostUpdateHashtagsRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostUpdateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostCreateResponseDTO;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostDetailResponseDTO;
import com.newcord.articleservice.domain.posts.entity.Posts;

public interface PostsComposeService {
    PostCreateResponseDTO createPost(String userID, PostCreateRequestDTO postCreateDTO);        //게시글 생성
    Posts updatePost(String userID, PostUpdateRequestDTO postUpdateDTO);        //게시글 수정
    String createPostEditSession(String articleID);        //게시글 편집 세션 생성
    String deletePostEditSession(String articleID);        //게시글 편집 세션 삭제
    PostDetailResponseDTO getPostDetail(Long postID);        //게시글 상세 검색 (ID 기반)
    PostDetailResponseDTO updateHashtags(String userID, PostUpdateHashtagsRequestDTO postUpdateHashtagsRequestDTO);        //해시태그 업데이트 (대체)


}
