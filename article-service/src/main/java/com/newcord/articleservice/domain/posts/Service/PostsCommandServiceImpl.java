package com.newcord.articleservice.domain.posts.Service;

import com.newcord.articleservice.domain.article_version.entity.OperationType;
import com.newcord.articleservice.domain.articles.dto.ArticleRequest.TitleUpdateRequestDTO;
import com.newcord.articleservice.domain.hashtags.entity.Hashtags;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostCreateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostUpdateRequestDTO;
import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.domain.posts.repository.PostsRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import java.util.List;
import java.util.Set;
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
    public Posts updateTitle(Long userID, Long postId, TitleUpdateRequestDTO titleUpdateRequestDTO) {
        Posts post = postsRepository.findById(postId).orElseThrow(() -> new ApiException(
                ErrorStatus._POSTS_NOT_FOUND));

        String title = post.getTitle();

        if(titleUpdateRequestDTO.getOperationType().equals(OperationType.INSERT)){
            StringBuffer sb = new StringBuffer(title);
            if(sb.length() < titleUpdateRequestDTO.getPosition().intValue()){
                sb.setLength(titleUpdateRequestDTO.getPosition().intValue());
                //문자열 연결하는데 인덱스 범위 초과하니 오류가 발생함
            }
            sb.insert(titleUpdateRequestDTO.getPosition().intValue(), titleUpdateRequestDTO.getContent());
            title = sb.toString();
        }
        else {
            StringBuffer sb = new StringBuffer(title);
            if(sb.length() < titleUpdateRequestDTO.getPosition().intValue()){
                sb.setLength(titleUpdateRequestDTO.getPosition().intValue());
                //문자열 연결하는데 인덱스 범위 초과하니 오류가 발생함
            }
            sb.delete(titleUpdateRequestDTO.getPosition().intValue(), titleUpdateRequestDTO.getPosition().intValue() + titleUpdateRequestDTO.getContent().length());
            title = sb.toString();
        }

        post.setTitle(title);

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
    public Posts addHashtags(Long postId, Hashtags hashtag) {
        Posts post = postsRepository.findById(postId).orElseThrow(() -> new ApiException(
                ErrorStatus._POSTS_NOT_FOUND));

        Set<Hashtags> hashtags = post.getHashtags();
        hashtags.add(hashtag);
        post.updateHashtagList(hashtags);

        postsRepository.save(post);

        return post;
    }

    @Override
    public Posts removeHashtags(Long postId, Hashtags hashtag) {
        Posts post = postsRepository.findById(postId).orElseThrow(() -> new ApiException(
            ErrorStatus._POSTS_NOT_FOUND));

        Set<Hashtags> hashtags = post.getHashtags();
        hashtags.remove(hashtag);
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

    @Override
    public Posts increaseView(Long postId) {
        Posts post = postsRepository.findById(postId).orElseThrow(() -> new ApiException(
                ErrorStatus._POSTS_NOT_FOUND));

        post.increaseViews();
        postsRepository.save(post);

        return post;
    }
}
