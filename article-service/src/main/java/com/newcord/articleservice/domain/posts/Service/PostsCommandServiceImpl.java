package com.newcord.articleservice.domain.posts.Service;

import com.newcord.articleservice.domain.editor.dto.EditorRequest.EditorAddRequestDTO;
import com.newcord.articleservice.domain.editor.editor.EditorCommandService;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostCreateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostCreateResponseDTO;
import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.domain.posts.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostsCommandServiceImpl implements PostsCommandService{
    private final PostsRepository postsRepository;
    private final EditorCommandService editorCommandService;
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

        return PostCreateResponseDTO.builder()
                .id(newPosts.getId())
                .build();
    }


}
