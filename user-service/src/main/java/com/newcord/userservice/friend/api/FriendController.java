package com.newcord.userservice.friend.api;

import com.newcord.userservice.friend.service.FriendComposeService;

import com.newcord.userservice.global.common.response.ApiResponse;
import com.newcord.userservice.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Friend", description = "친구 API")
@RequestMapping("/users/friend")
public class FriendController {

    private final UserService userService;
    private final FriendComposeService friendComposeService;

    @DeleteMapping("/{friendID}")
    @Operation(summary = "친구 삭제", description = "친구 삭제하기")
    public ApiResponse<?> deleteFriend(@PathVariable Long friendID){
        return ApiResponse.onSuccess(friendComposeService.deleteFriendship(friendID));
    }


    @PostMapping("/accept/{friendshipId}")
    @Operation(summary = "친구 수락",description = "친구 수락하기")
    public ApiResponse<?> approveFriendship (@Valid @PathVariable("friendshipId") Long friendshipId){
        return ApiResponse.onSuccess(friendComposeService.approveFriendshipRequest(friendshipId));
    }

    @PostMapping("/{fromid},{toid}")
    @Operation(summary = "친구 요청",description = "친구 요청하기")
    public ApiResponse<?> sendFriendshipRequest(@Valid @PathVariable("fromid") Long fromId, @PathVariable("toid") Long toId) {

        return ApiResponse.onSuccess(friendComposeService.requestFriendship(fromId,toId));
    }

    @GetMapping("/received/{userID}")
    @Operation(summary = "받은 요청",description = "받은 요청 보기")
    public ApiResponse<?> getWaitingFriendInfo(@PathVariable Long userID){
        return ApiResponse.onSuccess(friendComposeService.getWaitingFriendList(userID));
    }

    @DeleteMapping("/reject/{friendshipId}")
    @Operation(summary = "친구 거절",description = "친구 거절하기")
    public ApiResponse<?> rejectFriendship(@Valid @PathVariable("friendshipId") Long friendshipId){
        return ApiResponse.onSuccess(friendComposeService.rejectFriendshipRequest(friendshipId));
    }

    @GetMapping("/friend/{userID}")
    @Operation(summary = "친구 목록",description = "내 친구 목록보기")
    public ApiResponse<?> getAcceptFriendInfo(@PathVariable Long userID){
        return ApiResponse.onSuccess(friendComposeService.getAcceptFriendList(userID));
    }

}
