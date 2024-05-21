package com.newcord.articleservice.domain.posts.Service;

import com.newcord.articleservice.domain.hashtags.entity.Hashtags;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostCreateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostUpdateRequestDTO;
import com.newcord.articleservice.domain.posts.entity.Posts;
import java.util.List;

public interface PostsCommandService {

    Posts createPost(Long userID, PostCreateRequestDTO postCreateDTO);        //게시글 생성
    Posts updatePost(Long userID, PostUpdateRequestDTO postUpdateDTO);        //게시글 수정
    Posts updateHashtags(Long postId, List<Hashtags> hashtags);        //해시태그 업데이트 (대체)
    void deletePost(Long postId);        //게시글 삭제 (편집자 검증을 거치지 않음)
}
