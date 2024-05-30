package com.newcord.articleservice.domain.posts.Service;

import com.newcord.articleservice.domain.hashtags.entity.Hashtags;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostCreateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostUpdateRequestDTO;
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
@Transactional
public class PostsCommandServiceImpl implements PostsCommandService{
    private final PostsRepository postsRepository;

    /*
    TODO: 게시글 편집 세션(Exchange 생성 삭제등 관리에 관한 서비스 구현)
     */
    @Override
    public Posts createPost(Long userID, PostCreateRequestDTO postCreateDTO) {
        Posts newPosts = postCreateDTO.toEntity(postCreateDTO);
        postsRepository.save(newPosts);
        return newPosts;
    }

    @Override
    public Posts updatePost(Long userID, PostUpdateRequestDTO postUpdateDTO) {
        Posts post = postsRepository.findById(postUpdateDTO.getId()).orElseThrow(() -> new ApiException(
                ErrorStatus._POSTS_NOT_FOUND));

        post.updateByDTO(postUpdateDTO);
        postsRepository.save(post);

        return post;
    }

    @Override
    public Posts updateHashtags(Long postId, List<Hashtags> hashtags) {
        Posts post = postsRepository.findById(postId).orElseThrow(() -> new ApiException(
                ErrorStatus._POSTS_NOT_FOUND));

        post.updateHashtagList(hashtags);
        postsRepository.save(post);

        return post;
    }

    @Override
    public void deletePost(Long postId) {
        Posts post = postsRepository.findById(postId).orElseThrow(() -> new ApiException(
                ErrorStatus._POSTS_NOT_FOUND));
        postsRepository.delete(post);
    }
}
