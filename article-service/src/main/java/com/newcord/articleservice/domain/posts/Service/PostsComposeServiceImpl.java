package com.newcord.articleservice.domain.posts.Service;

import com.newcord.articleservice.domain.articles.service.ArticlesCommandService;
import com.newcord.articleservice.domain.editor.dto.EditorRequest.EditorAddRequestDTO;
import com.newcord.articleservice.domain.editor.service.EditorCommandService;
import com.newcord.articleservice.domain.editor.service.EditorQueryService;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostCreateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostUpdateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostCreateResponseDTO;
import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.rabbitMQ.Service.RabbitMQService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostsComposeServiceImpl implements PostsComposeService{
    private final PostsCommandService postsCommandService;

    private final EditorCommandService editorCommandService;
    private final EditorQueryService editorQueryService;

    private final ArticlesCommandService articlesCommandService;

    private final RabbitMQService rabbitMQService;
    @Override
    public PostCreateResponseDTO createPost(String userID, PostCreateRequestDTO postCreateDTO) {
        Posts posts = postsCommandService.createPost(userID, postCreateDTO);

        editorCommandService.addEditor(posts,EditorAddRequestDTO.builder()
            .postId(posts.getId())
            .userID(userID)
            .build());

        articlesCommandService.createArticle(posts.getId());

        return PostCreateResponseDTO.builder()
            .id(posts.getId())
            .build();
    }

    @Override
    public Posts updatePost(String userID, PostUpdateRequestDTO postUpdateDTO) {
        // 요청 유저의 권한 확인
        editorQueryService.getEditorByPostIdAndUserID(postUpdateDTO.getId(), userID);

        return postsCommandService.updatePost(userID, postUpdateDTO);
    }


    @Override
    public String createPostEditSession(String articleID) {
        rabbitMQService.createFanoutExchange(articleID);
        return "편집세션이 생성되었습니다.";
    }

    @Override
    public String deletePostEditSession(String articleID) {
        rabbitMQService.deleteTopic(articleID);
        return "편집세션이 삭제되었습니다.";
    }
}
