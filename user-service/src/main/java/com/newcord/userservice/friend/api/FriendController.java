package com.newcord.userservice.friend.api;

import com.newcord.userservice.friend.dto.FriendRequest.*;
import com.newcord.userservice.friend.dto.FriendResponse.*;
import com.newcord.userservice.friend.service.FriendCommandService;

import com.newcord.userservice.friend.service.FriendQueryService;
import com.newcord.userservice.global.common.response.ApiResponse;
import com.newcord.userservice.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Friend", description = "친구 API")
@RequestMapping("/users/friend")
public class FriendController {

    private final UserService userService;
    private final FriendCommandService friendCommandService;
    private final FriendQueryService friendQueryService;

    @DeleteMapping("/delete")
    @Operation(summary = "친구 삭제", description = "친구 삭제하기")
    public ApiResponse<FriendResponseDTO> deleteFriend(@RequestBody FriendRequestDTO friendRequestDTO){
        return ApiResponse.onSuccess(friendCommandService.deleteFriendship(friendRequestDTO));
    }


    @PostMapping("/accept")
    @Operation(summary = "친구 수락",description = "친구 수락하기")
    public ApiResponse<FriendResponseDTO> approveFriendship(@RequestBody FriendRequestDTO friendRequestDTO){
        log.info("Received request with id: " + friendRequestDTO.getId().toString());
        return ApiResponse.onSuccess(friendCommandService.approveFriendshipRequest(friendRequestDTO));
    }

    @PostMapping("/request")
    @Operation(summary = "친구 요청",description = "친구 요청하기")
    public ApiResponse<FriendResponseDTO> sendFriendshipRequest(@RequestBody CreateFriendRequestDTO createfriendRequestDTO) {

        return ApiResponse.onSuccess(friendCommandService.createFriendship(createfriendRequestDTO));
    }

    @GetMapping("/received/{userid}")
    @Operation(summary = "받은 요청",description = "받은 요청 보기")
    public ApiResponse<List<FriendResponseDTO>> getWaitingFriendInfo(@PathVariable Long userid){
        return ApiResponse.onSuccess(friendQueryService.getWaitingFriendList(userid));
    }

    @DeleteMapping("/reject")
    @Operation(summary = "친구 거절",description = "친구 거절하기")
    public ApiResponse<?> rejectFriendship(@RequestBody FriendRequestDTO friendRequestDTO){
        return ApiResponse.onSuccess(friendCommandService.rejectFriendshipRequest(friendRequestDTO));
    }

    @GetMapping("/friend/{userid}")
    @Operation(summary = "친구 목록",description = "내 친구 목록보기")
    public ApiResponse<List<FriendResponseDTO>> getAcceptFriendInfo(@PathVariable Long userid){
        return ApiResponse.onSuccess(friendQueryService.getAcceptFriendList(userid));
    }

}
