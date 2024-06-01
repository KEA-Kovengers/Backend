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
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import com.newcord.articleservice.global.rabbitMQ.Service.RabbitMQService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
@Transactional
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
    private static Map<String, Integer> sessionCnt = new HashMap<>();


    public String createPostEditSession(String articleID) {
        rabbitMQService.createFanoutExchange(articleID);

        if (sessionCnt.containsKey(articleID)) {
            sessionCnt.put(articleID, sessionCnt.get(articleID) + 1);
        } else {
            sessionCnt.put(articleID, 1);
        }

        return "편집세션이 생성되었습니다.";
    }

    @Override
    public String deletePostEditSession(String articleID) {
        rabbitMQService.deleteTopic(articleID);
        if (sessionCnt.get(articleID) > 0) {
            sessionCnt.put(articleID, sessionCnt.get(articleID) - 1);
        } else throw new ApiException(ErrorStatus._INTERNAL_SERVER_ERROR);
        return "편집세션이 삭제되었습니다.";
    }

/*
    15분 단위로 increaseId()함수 실행
    실행시에 현재 웹소켓 세선에 연결된 유저가 있으면
    articleVersion 업데이트
    유저 없으면 실행되도 업데이트 안됨
*/
@Scheduled(fixedRate = 540000)
    public void increaseId() {
        for (Map.Entry<String, Integer> entry : sessionCnt.entrySet()) {
            if (sessionCnt.get(entry.getKey()) > 0) {
                articleVersionCommandService.updateVersion(Long.valueOf(entry.getKey()));
            }
        }
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
                .created(posts.getCreated_at())
                .updated(posts.getUpdated_at())
                .build();
    }

    @Override
    public PostDetailResponseDTO getPostDetail(Long postId, String purpose,
        HttpServletRequest request, HttpServletResponse response) {
        Posts posts = postsQueryService.getPost(postId);

        if (purpose != null && purpose.equals("view")) {
            // 조회 수 중복 방지
            Cookie oldCookie = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("postView")) {
                        oldCookie = cookie;
                    }
                }
            }
            if (oldCookie != null) {
                if (!oldCookie.getValue().contains("["+ postId.toString() +"]")) {
                    posts = postsCommandService.increaseView(postId);
                    oldCookie.setValue(oldCookie.getValue() + "_[" + postId + "]");
                    oldCookie.setPath("/");
                    oldCookie.setMaxAge(60 * 60 * 24);
                    response.addCookie(oldCookie);
                }
            } else {
                posts = postsCommandService.increaseView(postId);
                Cookie newCookie = new Cookie("postView", "[" + postId + "]");
                newCookie.setPath("/");
                newCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(newCookie);
            }
        }

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


    private PostResponseDTO convertToDTO(Posts post) {
        return PostResponseDTO.builder()
                .id(post.getId())
                .views(post.getViews())
                .title(post.getTitle())
                .body(post.getBody())
                .thumbnail(post.getThumbnail())
                .build();
    }
}