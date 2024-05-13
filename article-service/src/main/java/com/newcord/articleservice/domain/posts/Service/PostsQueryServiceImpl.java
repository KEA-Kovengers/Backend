package com.newcord.articleservice.domain.posts.Service;

import com.newcord.articleservice.domain.articles.service.ArticlesQueryService;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockDTO;
import com.newcord.articleservice.domain.block.service.BlockQueryService;
import com.newcord.articleservice.domain.hashtags.entity.Hashtags;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostDetailResponseDTO;
import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.domain.posts.repository.PostsRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostsQueryServiceImpl implements PostsQueryService{
    private final PostsRepository postsRepository;

    @Override
    public Posts getPost(Long postId) {
        Posts posts = postsRepository.findById(postId).orElse(null);
        if(posts == null)
            throw new ApiException(ErrorStatus._POSTS_NOT_FOUND);;
        return posts;
    }
}
