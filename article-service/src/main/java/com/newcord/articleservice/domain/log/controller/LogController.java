package com.newcord.articleservice.domain.log.controller;

import com.newcord.articleservice.domain.block.service.BlockQueryService;
import com.newcord.articleservice.domain.editor.service.EditorComposeService;
import com.newcord.articleservice.domain.log.Service.LogComposeService;
import com.newcord.articleservice.domain.log.dto.LogResponse.*;
import com.newcord.articleservice.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "게시글 로그 분석 API", description = "게시글,블럭 로그 관련 API")
@RestController
@AllArgsConstructor
@RequestMapping("/log")
public class LogController {

    private BlockQueryService blockQueryService;
    private LogComposeService logComposeService;

    @Operation(summary = "공동작업자 로그 조회 API",description = "작업자들의 블럭 참여 목록을 조회합니다.")
    @GetMapping("/update/{postId}")
    public ApiResponse<EditorLogListResponseDTO> getEditorsLogData(@PathVariable Long postId){
        return ApiResponse.onSuccess(logComposeService.getEditorsLogData(postId));
    }

    @Operation(summary = "공동작업자 블럭 생성자 조회 API",description = "작업자들의 생성 블럭 목록을 조회합니다.")
    @GetMapping("/creator/{userid}")
    public ApiResponse<BlockLogListDataResponseDTO> getBlockCreator(@PathVariable Long userid){
        return ApiResponse.onSuccess(logComposeService.getBlockCreator(userid));
    }

    @Operation(summary = "공동 작업자들이 각자 생성한 블럭 조회 API",description = "작업자와 생성한 블럭 아이디를 반환합니다")
    @GetMapping("/{postID}")
    public ApiResponse<List<BlockCreatorDataResponseDTO>> getCreatorBlock(@PathVariable Long postID){
        return ApiResponse.onSuccess(logComposeService.getBlockCreatorData(postID));
    }

}
