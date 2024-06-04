package com.newcord.articleservice.domain.likes.controller;

import com.newcord.articleservice.domain.likes.dto.LikeRequest.*;
import com.newcord.articleservice.domain.likes.dto.LikeResponse;
import com.newcord.articleservice.domain.likes.entity.Likes;
import com.newcord.articleservice.domain.likes.service.LikeCommandService;
import com.newcord.articleservice.domain.likes.service.LikeComposeService;
import com.newcord.articleservice.domain.likes.service.LikeComposeServiceImpl;
import com.newcord.articleservice.domain.likes.service.LikeQueryService;
import com.newcord.articleservice.global.annotation.UserID;
import com.newcord.articleservice.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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
    private final LikeComposeService likeComposeService;

    @PostMapping("/create")
    @Operation(summary = "좋아요 생성",description = "좋아요 생성")
    public ApiResponse<Likes> createLike(
            @Schema(hidden = true) @UserID Long userID, @RequestBody CreateLikeRequestDTO createLikeRequestDTO){
        System.err.println("userID = " + userID);
        return ApiResponse.onSuccess(likeCommandService.createLike(userID,createLikeRequestDTO));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "좋아요 취소",description = "취소할 postid, 본인 userid 넣기")
    public ApiResponse<String> deleteLike(@RequestBody DeleteLikeRequestDTO deleteLikeRequestDTO){
        System.out.println("userid = " + deleteLikeRequestDTO);
        return ApiResponse.onSuccess(likeCommandService.deleteLike(deleteLikeRequestDTO));
    }

    @GetMapping("/likelist/{userid}")
    @Operation(summary = "유저의 좋아요 내역 조회",description = "유저 활동페이지 좋아요 목록 조회")
    public ApiResponse<List<LikeResponse.LikeResponseDTO>> getLikeList(@PathVariable Long userid){
        return ApiResponse.onSuccess(likeComposeService.getLikeList(userid));
    }

    @GetMapping("/userlist/{postid}")
    @Operation(summary = "게시글 좋아요 누른 유저 조회",description = "유저 목록 조회")
    public ApiResponse<List<Likes>> getUserList(@PathVariable Long postid){
        return ApiResponse.onSuccess(likeQueryService.getLikeUserid(postid));
    }
}
