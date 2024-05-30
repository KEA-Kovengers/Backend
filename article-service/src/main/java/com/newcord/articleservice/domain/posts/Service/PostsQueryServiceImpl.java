package com.newcord.articleservice.domain.posts.Service;

import com.newcord.articleservice.domain.posts.dto.PostResponse.*;
import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.domain.posts.enums.PostStatus;
import com.newcord.articleservice.domain.posts.repository.PostsRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

@Override
public SocialPostListDTO getPostList(Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page, size);
    Page<Posts> postsPage = postsRepository.findAll(pageRequest);

    List<PostResponseDTO> postResponseDTOList = postsPage.getContent().stream()
            .filter(post -> post.getStatus() == PostStatus.POST)
            .map(this::convertToDTO)
            .collect(Collectors.toList());


    Page<PostResponseDTO> postResponseDTOPage = new PageImpl<>(postResponseDTOList, pageRequest, postsPage.getTotalElements());

    return SocialPostListDTO.builder()
            .postsList(postResponseDTOPage)
            .build();
}
    private PostResponseDTO convertToDTO(Posts post) {
        return PostResponseDTO.builder()
                .id(post.getId())
                .views(post.getViews())
                .title(post.getTitle())
                .body(post.getBody())
                .thumbnail(post.getThumbnail())
                .status(post.getStatus())
                .build();
    }

    @Override
    public Page<Posts> getPostbyHashTag(String tag, Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page, size);

        return postsRepository.findPostsByHashtagName(tag,pageRequest);
    }

}
