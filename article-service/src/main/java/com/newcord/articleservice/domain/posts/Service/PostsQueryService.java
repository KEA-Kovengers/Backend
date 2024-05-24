package com.newcord.articleservice.domain.posts.Service;


import com.newcord.articleservice.domain.posts.dto.PostResponse;
import com.newcord.articleservice.domain.posts.entity.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostsQueryService {
    Posts getPost(Long postId);     //게시글 검색 (ID 기반)


    PostResponse.SocialPostListDTO getPostList(Integer page, Integer size);

    Page<Posts> getPostbyHashTag(String tag, Integer page, Integer size);
}
