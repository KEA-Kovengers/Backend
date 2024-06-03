package com.newcord.articleservice.domain.posts.Service;

import com.newcord.articleservice.domain.comments.repository.CommentsRepository;
import com.newcord.articleservice.domain.editor.repository.EditorRepository;
import com.newcord.articleservice.domain.likes.repository.LikeRepository;
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
private final LikeRepository likeRepository;
private final CommentsRepository commentsRepository;
private final EditorRepository editorRepository;
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
    Page<Posts> postsPage = postsRepository.findPosts(PostStatus.POST,pageRequest);

    List<SocialPostResponseDTO> postResponseDTOList = postsPage.getContent().stream()
            .map(post -> {
                SocialPostResponseDTO dto = convertToDTO(post);
                int likeCnt = likeRepository.findAllByPost_id(post.getId()).size();
                int commentCnt=commentsRepository.findByPostId(post.getId()).size();
                dto.setCommentCnt(commentCnt);
                dto.setLikeCnt(likeCnt);
                return dto;
            })
            .collect(Collectors.toList());


    Page<SocialPostResponseDTO> postResponseDTOPage = new PageImpl<>(postResponseDTOList, pageRequest, postsPage.getTotalElements());

    return SocialPostListDTO.builder()
            .postsList(postResponseDTOPage)
            .build();
}
    private SocialPostResponseDTO convertToDTO(Posts post) {

        return SocialPostResponseDTO.builder()
                .id(post.getId())
                .views(post.getViews())
                .title(post.getTitle())
                .body(post.getBody())
                .thumbnail(post.getThumbnail())
                .status(post.getStatus())
                .created(post.getCreated_at())
                .updated(post.getUpdated_at())
               .userId(editorRepository.finduserByPostId(post.getId()).getUserID())
                .build();
    }

    @Override
    public SocialPostListDTO getPostbyHashTag(String tag, Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Posts> postsPage = postsRepository.findPostsByHashtagName(tag,PostStatus.POST,pageRequest);

        List<SocialPostResponseDTO> postResponseDTOList = postsPage.getContent().stream()
                .map(post -> {
                    SocialPostResponseDTO dto = convertToDTO(post);
                    int likeCnt = likeRepository.findAllByPost_id(post.getId()).size();
                    int commentCnt=commentsRepository.findByPostId(post.getId()).size();
                    dto.setCommentCnt(commentCnt);
                    dto.setLikeCnt(likeCnt);
                    return dto;
                })
                .collect(Collectors.toList());


        Page<SocialPostResponseDTO> postResponseDTOPage = new PageImpl<>(postResponseDTOList, pageRequest, postsPage.getTotalElements());

        return SocialPostListDTO.builder()
                .postsList(postResponseDTOPage)
                .build();
    }

}
