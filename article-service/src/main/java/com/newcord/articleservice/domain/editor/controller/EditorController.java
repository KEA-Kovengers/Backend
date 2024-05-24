package com.newcord.articleservice.domain.editor.controller;

import com.newcord.articleservice.domain.block.dto.BlockResponse.*;
import com.newcord.articleservice.domain.block.service.BlockCommandService;
import com.newcord.articleservice.domain.editor.dto.EditorRequest.DeleteEditorRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorRequest.EditorAddRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.*;
import com.newcord.articleservice.domain.editor.service.EditorCommandService;
import com.newcord.articleservice.domain.editor.service.EditorComposeService;
import com.newcord.articleservice.domain.editor.service.EditorQueryService;
import com.newcord.articleservice.domain.posts.Service.PostsCommandService;
import com.newcord.articleservice.global.annotation.UserID;
import com.newcord.articleservice.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "게시글 편집자(참여자) API", description = "편집자(참여자) 관련 API")
@RestController
@AllArgsConstructor
@RequestMapping("/editor")
public class EditorController {
    private final EditorComposeService editorComposeService;
    private final EditorQueryService editorQueryService;

    @Operation(summary = "편집자 삭제 API", description = "게시글 편집자 목록에서 입력받은 유저를 삭제합니다. 본인 삭제 외에 다른 유저 삭제용으로도 사용 가능합니다.")
    @DeleteMapping("/deleteUser")
    public ApiResponse<DeleteEditorResponseDTO> deleteUser(@UserID Long userID, @RequestBody DeleteEditorRequestDTO deleteEditorRequestDTO) {

        return ApiResponse.onSuccess(editorComposeService.deleteEditor(userID, deleteEditorRequestDTO));
    }

    @Operation(summary = "편집자 추가 API", description = "게시글 편집자 목록에 입력받은 유저를 추가합니다. 공동작업자 초대할때 사용합니다.")
    @PostMapping("/addUser")
    public ApiResponse<EditorAddResponseDTO> addEditor(@UserID Long userID,@RequestBody EditorAddRequestDTO addRequestDTO) {
        return ApiResponse.onSuccess(editorComposeService.addEditor(userID, addRequestDTO));
    }

    @Operation(summary = "공동작업자 조회 API", description = "게시글의 공동작업자 목록을 조회합니다.")
    @GetMapping("/list/{postId}")
    public ApiResponse<EditorListResponseDTO> getEditorList(@PathVariable Long postId) {
        return ApiResponse.onSuccess(editorQueryService.getAllEditorsByPostId(postId));
    }

    @Operation(summary = "공동작업자 로그 조회 API",description = "작업자들의 블럭 업데이트 목록을 조회합니다.")
    @GetMapping("/log/{postId}")
    public ApiResponse<List<EditorLogResponseDTO>> getEditorsLogData(@PathVariable Long postId){
        return ApiResponse.onSuccess(editorComposeService.getEditorsLogData(postId));
    }




}
