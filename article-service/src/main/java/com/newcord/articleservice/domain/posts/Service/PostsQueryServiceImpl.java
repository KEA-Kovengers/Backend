package com.newcord.articleservice.domain.posts.Service;

import com.newcord.articleservice.domain.posts.dto.PostResponse.*;
import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.domain.posts.repository.PostsRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;

import java.util.ArrayList;
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

    //소셜피드에서 게시글 전체조회
    @Override
    public List<PostResponseDTO> getPostList(){
        List<Posts> posts=postsRepository.findAll();
        List<PostResponseDTO> result=new ArrayList<>();
        for(Posts p:posts){
        result.add(toDTO(p));
        }
        return result;
    }

    public PostResponseDTO toDTO(Posts post){
        return PostResponseDTO.builder()
                .id(post.getId())
                .body(post.getBody())
                .title(post.getTitle())
                .views(post.getViews())
                .thumbnail(post.getThumbnail())
                .build();
    }


    @Override
    public List<Posts> getPostbyHashTag(String tag){
        return postsRepository.findPostsByHashtagName(tag);
    }

}
