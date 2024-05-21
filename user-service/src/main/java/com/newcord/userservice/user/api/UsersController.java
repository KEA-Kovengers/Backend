package com.newcord.userservice.user.api;

import com.newcord.userservice.global.common.response.ApiResponse;
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
    public ApiResponse<UsersResponseDTO> updateUser(@RequestBody UsersRequestDTO usersRequestDTO) {
        return ApiResponse.onSuccess(usersCommandService.updateUserInfo(usersRequestDTO));
    }

}
