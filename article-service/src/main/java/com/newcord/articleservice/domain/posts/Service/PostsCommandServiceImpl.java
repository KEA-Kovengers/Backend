package com.newcord.articleservice.domain.posts.Service;

import com.newcord.articleservice.domain.articles.service.ArticlesCommandService;
import com.newcord.articleservice.domain.editor.dto.EditorRequest.EditorAddRequestDTO;
import com.newcord.articleservice.domain.editor.service.EditorCommandService;
import com.newcord.articleservice.domain.editor.service.EditorQueryService;
import com.newcord.articleservice.domain.hashtags.entity.Hashtags;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostCreateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostUpdateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostCreateResponseDTO;
import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.domain.posts.repository.PostsRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import com.newcord.articleservice.rabbitMQ.Service.RabbitMQService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostsCommandServiceImpl implements PostsCommandService{
    private final PostsRepository postsRepository;

    /*
    TODO: 게시글 편집 세션(Exchange 생성 삭제등 관리에 관한 서비스 구현)
     */
    @Override
    public Posts createPost(String userID, PostCreateRequestDTO postCreateDTO) {
        Posts newPosts = postCreateDTO.toEntity(postCreateDTO);
        postsRepository.save(newPosts);

        return newPosts;
    }

    @Override
    public Posts updatePost(String userID, PostUpdateRequestDTO postUpdateDTO) {
        Posts post = postsRepository.findById(postUpdateDTO.getId()).orElseThrow(() -> new ApiException(
                ErrorStatus._POSTS_NOT_FOUND));

        post.updateByDTO(postUpdateDTO);
        postsRepository.save(post);

        return post;
    }

    @Override
    public Posts addHashtags(Long postId, List<Hashtags> hashtags) {
        Posts post = postsRepository.findById(postId).orElseThrow(() -> new ApiException(
                ErrorStatus._POSTS_NOT_FOUND));

        hashtags.forEach(post::addHashtags);
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
