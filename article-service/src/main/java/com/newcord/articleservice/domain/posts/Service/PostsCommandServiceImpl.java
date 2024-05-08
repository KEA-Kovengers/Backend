package com.newcord.articleservice.domain.posts.Service;

import com.newcord.articleservice.domain.articles.service.ArticlesCommandService;
import com.newcord.articleservice.domain.editor.dto.EditorRequest.EditorAddRequestDTO;
import com.newcord.articleservice.domain.editor.service.EditorCommandService;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostCreateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostUpdateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostCreateResponseDTO;
import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.domain.posts.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostsCommandServiceImpl implements PostsCommandService{
    private final PostsRepository postsRepository;
    private final PostsQueryService postsQueryService;
    private final EditorCommandService editorCommandService;
    private final ArticlesCommandService articlesCommandService;
    /*
    TODO: 게시글 편집 세션(Exchange 생성 삭제등 관리에 관한 서비스 구현)
     */
    @Override
    public PostCreateResponseDTO createPost(String userID, PostCreateRequestDTO postCreateDTO) {
        Posts newPosts = postCreateDTO.toEntity(postCreateDTO);
        postsRepository.save(newPosts);
        editorCommandService.addEditor(EditorAddRequestDTO.builder()
                .postId(newPosts.getId())
                .userID(userID)
            .build());


        articlesCommandService.createArticle(newPosts.getId());

        return PostCreateResponseDTO.builder()
                .id(newPosts.getId())
                .build();
    }

    @Override
    public Posts updatePost(String userID, PostUpdateRequestDTO postUpdateDTO) {
        Posts post = postsQueryService.getPost(postUpdateDTO.getId());
        post.updateByDTO(postUpdateDTO);

        postsRepository.save(post);

        return post;
    }

    @Override
    public void deletePost(String userID, Long postId) {
        Posts post = postsQueryService.getPost(postId);
        postsRepository.delete(post);
    }
}
