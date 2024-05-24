package com.newcord.articleservice.domain.posts.Service;


import com.newcord.articleservice.domain.posts.dto.PostResponse;
import com.newcord.articleservice.domain.posts.entity.Posts;

import java.util.List;

public interface PostsQueryService {
    Posts getPost(Long postId);     //게시글 검색 (ID 기반)

    List<PostResponse.PostResponseDTO> getPostList();

    List<Posts> getPostbyHashTag(String tag);
}
