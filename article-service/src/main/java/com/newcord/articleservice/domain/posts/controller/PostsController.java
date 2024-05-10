package com.newcord.articleservice.domain.posts.controller;

import com.newcord.articleservice.domain.editor.service.EditorQueryService;
import com.newcord.articleservice.domain.posts.Service.PostsCommandService;
import com.newcord.articleservice.domain.posts.Service.PostsQueryService;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostCreateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostUpdateRequestDTO;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostCreateResponseDTO;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostDetailResponseDTO;
import com.newcord.articleservice.domain.posts.dto.PostResponse.PostListResponseDTO;
import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private final PostsCommandService postsCommandService;
    private final PostsQueryService postsQueryService;
    private final EditorQueryService editorQueryService;

    @Operation(summary = "게시글 편집 세션 생성", description = "게시글 편집 세션을 생성합니다.")
    @PostMapping("/createEditSession")
    public ApiResponse<String> createPostEditSession(@RequestBody String articleID) {
        return ApiResponse.onSuccess(postsCommandService.createPostEditSession(articleID));
    }

    @Operation(summary = "게시글 편집 세션 삭제", description = "게시글 편집 세션을 삭제합니다.")
    @PostMapping("/deleteEditSession")
    public ApiResponse<String> deletePostEditSession(@RequestBody String articleID) {
        return ApiResponse.onSuccess(postsCommandService.deletePostEditSession(articleID));
    }

    @Operation(summary = "게시글 생성", description = "게시글을 생성합니다.")
    @PostMapping("/createPost")
    public ApiResponse<PostCreateResponseDTO> createPost(@RequestBody PostCreateRequestDTO postCreateRequestDTO) {
        return ApiResponse.onSuccess(postsCommandService.createPost("testID", postCreateRequestDTO));
    }

    @Operation(summary = "게시글 조회", description = "게시글을 조회합니다.")
    @GetMapping("/{postID}")
    public ApiResponse<PostDetailResponseDTO> getPost(@PathVariable Long postID) {
        return ApiResponse.onSuccess(postsQueryService.getPostDetail(postID));
    }

    @Operation(summary = "게시글 목록 조회", description = "유저의 게시글 목록을 조회합니다.")
    @GetMapping("/list/{userID}")
    public ApiResponse<PostListResponseDTO> getPostList(@PathVariable String userID, @RequestParam Integer page, @RequestParam Integer size){
        return ApiResponse.onSuccess(editorQueryService.getPostListByUserID(userID, page, size));
    }

    @Operation(summary = "게시글 편집", description = "게시글을 편집합니다.")
    @PostMapping("/editPost")
    public ApiResponse<Posts> editPost(@RequestBody PostUpdateRequestDTO updateRequestDTO) {
        return ApiResponse.onSuccess(postsCommandService.updatePost("testID", updateRequestDTO));
    }

}
