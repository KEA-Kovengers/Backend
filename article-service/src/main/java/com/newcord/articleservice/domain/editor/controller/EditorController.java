package com.newcord.articleservice.domain.editor.controller;

import com.newcord.articleservice.domain.editor.dto.EditorRequest.DeleteEditorRequestDTO;
import com.newcord.articleservice.domain.editor.dto.EditorResponse.DeleteEditorResponseDTO;
import com.newcord.articleservice.domain.editor.service.EditorCommandService;
import com.newcord.articleservice.domain.posts.Service.PostsCommandService;
import com.newcord.articleservice.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게시글 편집자(참여자) API", description = "편집자(참여자) 관련 API")
@RestController
@AllArgsConstructor
@RequestMapping("/editor")
public class EditorController {
    private final EditorCommandService editorCommandService;
    private final PostsCommandService postsCommandService;

    @Operation(summary = "편집자 삭제 API", description = "게시글 편집자 목록에서 입력받은 유저를 삭제합니다. 본인 삭제 외에 다른 유저 삭제용으로도 사용 가능합니다.")
    @DeleteMapping("/deleteUser")
    public ApiResponse<DeleteEditorResponseDTO> deleteUser(@RequestBody DeleteEditorRequestDTO deleteEditorRequestDTO) {
        DeleteEditorResponseDTO response = editorCommandService.deleteEditor("testID", deleteEditorRequestDTO);
        if(response.isPostDelete()){
            postsCommandService.deletePost("testID", deleteEditorRequestDTO.getPostId());
        }

        return ApiResponse.onSuccess(response);
    }

}
