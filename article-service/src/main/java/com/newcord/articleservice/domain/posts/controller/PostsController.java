package com.newcord.articleservice.domain.posts.controller;

import com.newcord.articleservice.domain.editor.dto.EditorResponse;
import com.newcord.articleservice.domain.editor.service.EditorQueryService;
import com.newcord.articleservice.domain.posts.Service.PostsCommandService;
import com.newcord.articleservice.domain.posts.Service.PostsComposeService;
import com.newcord.articleservice.domain.posts.Service.PostsQueryService;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostCreateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostUpdateHashtagsRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostUpdateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostResponse;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostCreateResponseDTO;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostDetailResponseDTO;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostListResponseDTO;
import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.global.annotation.UserID;
import com.newcord.articleservice.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Posts", description = "게시글 관련 REST API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostsController {
    private final PostsComposeService postsComposeService;
    private final EditorQueryService editorQueryService;
    private final PostsQueryService postsQueryService;

    @Operation(summary = "게시글 편집 세션 생성", description = "게시글 편집 세션을 생성합니다.")
    @PostMapping("/createEditSession")
    public ApiResponse<String> createPostEditSession(@RequestBody String articleID) {
        return ApiResponse.onSuccess(postsComposeService.createPostEditSession(articleID));
    }

    @Operation(summary = "게시글 편집 세션 삭제", description = "게시글 편집 세션을 삭제합니다.")
    @PostMapping("/deleteEditSession")
    public ApiResponse<String> deletePostEditSession(@RequestBody String articleID) {
        return ApiResponse.onSuccess(postsComposeService.deletePostEditSession(articleID));
    }

    @Operation(summary = "게시글 생성", description = "게시글을 생성합니다.")
    @PostMapping("/createPost")
    public ApiResponse<PostCreateResponseDTO> createPost(@Schema(hidden = true) @UserID Long userID, @RequestBody PostCreateRequestDTO postCreateRequestDTO) {
        return ApiResponse.onSuccess(postsComposeService.createPost(userID, postCreateRequestDTO));
    }

    @Operation(summary = "게시글 조회", description = "게시글을 조회합니다.")
    @GetMapping("/{postID}")
    public ApiResponse<PostDetailResponseDTO> getPost(@Schema(hidden = true) @UserID Long userID, @PathVariable Long postID, @RequestParam(required = false) String purpose) {
        return ApiResponse.onSuccess(postsComposeService.getPostDetail(postID, purpose));
    }

    @Operation(summary = "게시글 목록 조회", description = "유저의 게시글 목록을 조회합니다.")
    @GetMapping("/list/{userID}")
    public ApiResponse<EditorResponse.EditorPostListResponseDTO> getPostList(@PathVariable Long userID, @RequestParam Integer page, @RequestParam Integer size){
        return ApiResponse.onSuccess(editorQueryService.getPostListByUserID(userID, page, size));
    }

    @Operation(summary = "소셜 피드 전체조회", description = "소셜 피드 게시글을 전체 조회합니다")
    @GetMapping("/social")
    public ApiResponse<PostResponse.SocialPostListDTO> getSocialFeed(@RequestParam Integer page, @RequestParam Integer size){
        return ApiResponse.onSuccess(postsQueryService.getPostList(page,size));
    }

    @Operation(summary = "소셜 피드 해시태그로 조회", description = "소셜 피드 게시글을 해시태그별로 조회합니다")
    @GetMapping("/social/{tag}")
    public ApiResponse<PostResponse.SocialPostListDTO> getPostByTag(@PathVariable String tag, @RequestParam Integer page, @RequestParam Integer size){
        return ApiResponse.onSuccess(postsQueryService.getPostbyHashTag(tag,page,size));
    }


    @Operation(summary = "게시글 편집", description = "게시글을 편집합니다.")
    @PostMapping("/editPost")
    public ApiResponse<Posts> editPost(@Schema(hidden = true) @UserID Long userID, @RequestBody PostUpdateRequestDTO updateRequestDTO) {
        return ApiResponse.onSuccess(postsComposeService.updatePost(userID, updateRequestDTO));
    }

    @Operation(summary = "게시글 해시태그 수정", description = "게시글에 해시태그를 수정합니다. 입력된 리스트 그대로 저장되기에 게시글에 대한 해시태그를 모두 입력바랍니다.")
    @PostMapping("/updateHashtags")
    public ApiResponse<PostDetailResponseDTO> updateHashtags(@Schema(hidden = true) @UserID Long userID, @RequestBody PostUpdateHashtagsRequestDTO hashtagsRequestDTO) {
        return ApiResponse.onSuccess(postsComposeService.updateHashtags(userID, hashtagsRequestDTO));
    }
}
