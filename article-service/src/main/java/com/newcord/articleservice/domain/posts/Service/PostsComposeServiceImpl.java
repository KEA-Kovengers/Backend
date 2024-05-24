package com.newcord.articleservice.domain.posts.Service;

import com.newcord.articleservice.domain.article_version.service.ArticleVersionCommandService;
import com.newcord.articleservice.domain.articles.service.ArticlesCommandService;
import com.newcord.articleservice.domain.articles.service.ArticlesQueryService;
import com.newcord.articleservice.domain.block.dto.BlockResponse.BlockDTO;
import com.newcord.articleservice.domain.block.service.BlockQueryService;
import com.newcord.articleservice.domain.editor.dto.EditorRequest.EditorAddRequestDTO;
import com.newcord.articleservice.domain.editor.service.EditorCommandService;
import com.newcord.articleservice.domain.editor.service.EditorQueryService;
import com.newcord.articleservice.domain.hashtags.entity.Hashtags;
import com.newcord.articleservice.domain.hashtags.service.HashtagsCommandService;
import com.newcord.articleservice.domain.hashtags.service.HashtagsQueryService;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostCreateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostUpdateHashtagsRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostUpdateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostResponse.*;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostCreateResponseDTO;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostDetailResponseDTO;
import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.global.rabbitMQ.Service.RabbitMQService;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostsComposeServiceImpl implements PostsComposeService {
    private final PostsCommandService postsCommandService;
    private final PostsQueryService postsQueryService;

    private final EditorCommandService editorCommandService;
    private final EditorQueryService editorQueryService;

    private final ArticlesCommandService articlesCommandService;

    private final HashtagsQueryService hashtagsQueryService;
    private final HashtagsCommandService hashtagsCommandService;

    private final ArticlesQueryService articlesQueryService;
    private final BlockQueryService blockQueryService;

    private final ArticleVersionCommandService articleVersionCommandService;

    private final RabbitMQService rabbitMQService;

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

    @Override
    public PostCreateResponseDTO createPost(Long userID, PostCreateRequestDTO postCreateDTO) {
        Posts posts = postsCommandService.createPost(userID, postCreateDTO);

        // 해시태그 조회 (없으면 생성)
        List<Hashtags> hashtags = new ArrayList<>();
        for (String tagName : postCreateDTO.getHashtags()) {
            Hashtags tag = hashtagsQueryService.findByTagNameOptional(tagName).orElse(null);
            if (tag == null) {
                tag = hashtagsCommandService.createHashtags(tagName);
            }
            hashtags.add(tag);
        }

        // 해시태그 추가
        posts = postsCommandService.updateHashtags(posts.getId(), hashtags);

        editorCommandService.addEditor(posts, EditorAddRequestDTO.builder()
                .postId(posts.getId())
                .userID(userID)
                .build());

        articlesCommandService.createArticle(posts.getId());

        articleVersionCommandService.createArticleVersion(posts.getId());

        return PostCreateResponseDTO.builder()
                .id(posts.getId())
                .build();
    }

    @Override
    public Posts updatePost(Long userID, PostUpdateRequestDTO postUpdateDTO) {
        // 요청 유저의 권한 확인
        editorQueryService.getEditorByPostIdAndUserID(postUpdateDTO.getId(), userID);

        return postsCommandService.updatePost(userID, postUpdateDTO);
    }

    private PostDetailResponseDTO makePostDetailResponseDTO(Posts posts) {
        List<String> blockSequence = articlesQueryService.findArticleById(posts.getId()).getBlock_list();
        List<BlockDTO> blockDTOList = blockSequence.stream().map(blockQueryService::getBlockDetail).toList();

        return PostDetailResponseDTO.builder()
                .id(posts.getId())
                .thumbnail(posts.getThumbnail())
                .title(posts.getTitle())
                .articleVersion(articleVersionCommandService.getLatestVersion(posts.getId()))
                .body(posts.getBody())
                .status(posts.getStatus())
                .views(posts.getViews())
                .blockSequence(blockSequence)
                .blockList(blockDTOList)
                .hashtags(posts.getHashtags().stream().map(Hashtags::getTagName).toList())
                .build();
    }

    @Override
    public PostDetailResponseDTO getPostDetail(Long postId) {
        Posts posts = postsQueryService.getPost(postId);

        return makePostDetailResponseDTO(posts);
    }

    @Override
    public PostDetailResponseDTO updateHashtags(Long userID,
                                                PostUpdateHashtagsRequestDTO postUpdateHashtagsRequestDTO) {
        // 요청 유저의 권한 확인
        editorQueryService.getEditorByPostIdAndUserID(postUpdateHashtagsRequestDTO.getPostId(), userID);

        List<Hashtags> hashtags = new ArrayList<>();
        for (String tagName : postUpdateHashtagsRequestDTO.getHashtags()) {
            Hashtags tag = hashtagsQueryService.findByTagNameOptional(tagName).orElse(null);
            if (tag == null) {
                tag = hashtagsCommandService.createHashtags(tagName);
            }
            hashtags.add(tag);
        }

        Posts posts = postsCommandService.updateHashtags(postUpdateHashtagsRequestDTO.getPostId(), hashtags);

        return makePostDetailResponseDTO(posts);
    }

    @Override
    public List<PostDetailResponseDTO> getPostByHashTag(String TagName) {
        List<PostDetailResponseDTO> result = new ArrayList<>();
        List<Posts> post = postsQueryService.getPostbyHashTag(TagName);

        for (Posts p : post) {
            PostDetailResponseDTO postDetailResponseDTO = PostDetailResponseDTO.builder()
                    .id(p.getId())
                    .views(p.getViews())
                    .title(p.getTitle())
                    .body(p.getBody())
                    .thumbnail(p.getThumbnail())
                    .build();
            result.add(postDetailResponseDTO);
        }
        return result;
    }
}
