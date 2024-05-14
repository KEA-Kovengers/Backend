package com.newcord.userservice.folder.api;

import com.newcord.userservice.folder.domain.Folder;
import com.newcord.userservice.folder.domain.FolderPost;
import com.newcord.userservice.folder.dto.FolderPostRequest.FolderPostRequestDTO;
import com.newcord.userservice.folder.dto.FolderPostResponse.FolderPostResponseDTO;
import com.newcord.userservice.folder.dto.FolderRequest.FolderUpdateRequestDTO;
import com.newcord.userservice.folder.dto.FolderRequest.FolderRequestDTO;
import com.newcord.userservice.folder.dto.FolderResponse.FolderUpdateResponseDTO;
import com.newcord.userservice.folder.dto.FolderResponse.FolderResponseDTO;
import com.newcord.userservice.folder.service.FolderService;
import com.newcord.userservice.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users/folder")
@Tag(name = "Folder", description = "폴더 API")
public class FolderController {

    private final FolderService folderService;

    @Operation(summary = "폴더 생성", description = "폴더를 생성합니다.")
    @PostMapping("/add")
    public ApiResponse<FolderResponseDTO> addFolder(@RequestBody FolderRequestDTO folderAddRequestDTO) {
        return ApiResponse.onSuccess(folderService.addFolder(folderAddRequestDTO));
    }

    @Operation(summary = "폴더 삭제", description = "폴더를 삭제합니다.")
    @PostMapping("/delete")
    public ApiResponse<FolderResponseDTO> deleteFolder(@RequestBody FolderRequestDTO folderDeleteRequestDTO) {
        return ApiResponse.onSuccess(folderService.deleteFolder(folderDeleteRequestDTO));
    }

    @Operation(summary = "폴더 조회", description = "유저가 가진 폴더 목록을 조회합니다.")
    @GetMapping("/{userId}")
    public ApiResponse<List> getFolderList(@PathVariable Long userId) {
        return ApiResponse.onSuccess(folderService.getFolderList(userId));
    }

    @Operation(summary = "폴더 별 게시글 추가", description = "폴더에 게시글을 추가합니다.")
    @PostMapping("/addPost")
    public ApiResponse<FolderPostResponseDTO> addFolderPost(@RequestBody FolderPostRequestDTO folderPostAddDTO) {
        return ApiResponse.onSuccess(folderService.addFolderPost(folderPostAddDTO));
    }

    @Operation(summary = "폴더 별 게시글 삭제", description = "폴더의 게시글을 삭제합니다.")
    @PostMapping("/deletePost")
    public ApiResponse<FolderPostResponseDTO> deleteFolderPost(@RequestBody FolderPostRequestDTO folderPostDeleteDTO) {
        return ApiResponse.onSuccess(folderService.deleteFolderPost(folderPostDeleteDTO));
    }

    @Operation(summary = "폴더 별 게시글 조회", description = "폴더에 속해있는 게시글 목록을 조회합니다.")
    @GetMapping("/post/{folderId}")
    public ApiResponse<List> getFolderPostList(@PathVariable Long folderId) {
        return ApiResponse.onSuccess(folderService.getFolderPostList(folderId));
    }

    @Operation(summary = "폴더 업데이트", description = "폴더 명과 폴더 별 게시글을 수정합니다.")
    @PostMapping("/update")
    public ApiResponse<FolderUpdateResponseDTO> updateFolder(@RequestBody FolderUpdateRequestDTO folderUpdateRequestDTO) {
        return ApiResponse.onSuccess(folderService.updateFolder(folderUpdateRequestDTO));
    }
}