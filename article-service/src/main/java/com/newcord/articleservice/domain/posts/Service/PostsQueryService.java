package com.newcord.articleservice.domain.posts.Service;

import com.newcord.articleservice.domain.posts.entity.Posts;

public interface PostsQueryService {
    Posts getPost(Long postId);     //게시글 검색 (ID 기반)

}
