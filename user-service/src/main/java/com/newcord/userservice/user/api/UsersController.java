package com.newcord.userservice.user.api;

import com.newcord.userservice.auth.annotation.UserID;
import com.newcord.userservice.global.common.response.ApiResponse;
import com.newcord.userservice.user.dto.UsersRequest.UsersNameRequestDTO;
import com.newcord.userservice.user.dto.UsersRequest.UsersRequestDTO;
import com.newcord.userservice.user.dto.UsersResponse.UsersResponseDTO;
import com.newcord.userservice.user.service.UsersQueryService;
import com.newcord.userservice.user.service.UsersCommandService;
import groovy.util.logging.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
@Tag(name = "Users", description = "유저 API")
public class UsersController {
    private final UsersQueryService usersQueryService;
    private final UsersCommandService usersCommandService;

    @Operation(summary = "유저 정보 조회", description = "유저의 정보를 조회합니다.")
    @GetMapping("/{userId}")
    public ApiResponse<UsersResponseDTO> getUserInfo(@PathVariable Long userId) {
        return ApiResponse.onSuccess(usersQueryService.getUserInfo(userId));
    }

    @Operation(summary = "유저 정보 업데이트", description = "유저의 정보를 수정합니다.")
    @PostMapping("/update")
    public ApiResponse<UsersResponseDTO> updateUserInfo(@UserID Long userID, @RequestBody UsersRequestDTO usersRequestDTO) {
        return ApiResponse.onSuccess(usersCommandService.updateUserInfo(userID, usersRequestDTO));
    }

    @Operation(summary = "유저 프로필사진 업데이트", description = "유저의 프로필 사진를 수정합니다.")
    @PostMapping("/updateImg")
    public ApiResponse<UsersResponseDTO> updateUserImg(@UserID Long userID, @RequestParam String profileImg) {
        return ApiResponse.onSuccess(usersCommandService.updateUserImg(userID, profileImg));
    }

    @Operation(summary = "유저 명칭 업데이트", description = "유저의 닉네임과 블로그 이름을 수정합니다.")
    @PostMapping("/updateName")
    public ApiResponse<UsersResponseDTO> updateUserName(@UserID Long userID, @RequestBody UsersNameRequestDTO usersNameRequestDTO) {
        return ApiResponse.onSuccess(usersCommandService.updateUserName(userID, usersNameRequestDTO));
    }

}
