package com.newcord.articleservice.domain.comments.controller;

import com.newcord.articleservice.domain.comments.dto.CommentsRequest.*;
import com.newcord.articleservice.domain.comments.dto.CommentsResponse;
import com.newcord.articleservice.domain.comments.entity.Comments;
import com.newcord.articleservice.domain.comments.service.CommentsCommandService;
import com.newcord.articleservice.domain.comments.service.CommentsQueryService;
import com.newcord.articleservice.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Comment", description = "댓글 API")
@RequestMapping("/articles/comment")
public class CommentsController {

    private final CommentsCommandService commentsCommandService;
    private final CommentsQueryService commentsQueryService;

    @PostMapping("/delete")
    @Operation(summary = "댓글 삭제",description = "댓글 삭제하기")
    public ApiResponse<Comments> deleteComment(@RequestBody CommentsRequestDTO commentsRequestDTO){
        return ApiResponse.onSuccess(commentsCommandService.deleteComment(commentsRequestDTO));
    }

    @PostMapping("/create")
    @Operation(summary = "댓글 생성",description = "댓글일 경우 commentID=null, 대댓글일 경우 댓글 id를 넣어주세요")
    public ApiResponse<Comments> createComment(@RequestBody CommentsCreateRequestDTO commentsCreateRequestDTO){
        return ApiResponse.onSuccess(commentsCommandService.createComment(commentsCreateRequestDTO));
    }

    @GetMapping("/commentlist/{postid}")
    @Operation(summary = "게시글 댓글 보기", description = "게시글 댓글 보기")
    public ApiResponse<List<Comments>> getCommentsList(@PathVariable Long postid){
        return ApiResponse.onSuccess(commentsQueryService.getCommentsList(postid));
    }


}
