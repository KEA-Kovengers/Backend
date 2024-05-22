package com.newcord.articleservice.domain.likes.controller;

import com.newcord.articleservice.domain.likes.dto.LikeRequest.*;
import com.newcord.articleservice.domain.likes.entity.Likes;
import com.newcord.articleservice.domain.likes.service.LikeCommandService;
import com.newcord.articleservice.domain.likes.service.LikeQueryService;
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
@Tag(name = "Like", description = "좋아요 API")
@RequestMapping("/articles/like")
public class LikeController {

    private final LikeCommandService likeCommandService;
    private final LikeQueryService likeQueryService;

    @PostMapping("/create")
    @Operation(summary = "좋아요 생성",description = "좋아요 생성")
    public ApiResponse<Likes> createLike(@RequestBody CreateLikeRequestDTO createLikeRequestDTO){
        return ApiResponse.onSuccess(likeCommandService.createLike(createLikeRequestDTO));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "좋아요 취소",description = "좋아요 취소")
    public ApiResponse<String> deleteLike(@RequestBody Long id){
        return ApiResponse.onSuccess(likeCommandService.deleteLike(id));
    }

    @GetMapping("/{userid}")
    @Operation(summary = "좋아요 내역 조회",description = "내가 한 좋아요 목록 조회")
    public ApiResponse<List<Likes>> getLikeList(@PathVariable Long userid){
        return ApiResponse.onSuccess(likeQueryService.getLikeList(userid));
    }
}
