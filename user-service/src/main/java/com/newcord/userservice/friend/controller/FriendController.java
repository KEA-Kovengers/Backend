package com.newcord.userservice.friend.controller;
import com.newcord.userservice.friend.domain.Friend;
import com.newcord.userservice.friend.repository.FriendRepository;
import com.newcord.userservice.friend.service.FriendService;
import com.newcord.userservice.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@Slf4j
@Tag(name = "Friend", description = "친구 API")
@RequestMapping("/api/friend")
public class FriendController {

    @Autowired
    private final FriendRepository friendRepository;

    private final FriendService friendService;
    private final UserService userService;

    public FriendController(FriendRepository friendRepository, FriendService friendService, UserService userService) {
        this.friendRepository = friendRepository;
        this.friendService = friendService;
        this.userService = userService;
    }

//    @GetMapping("/api/{userID}/friends")
//    @Operation(summary = "친구 목록 조회", description = "유저의 친구 목록을 불러옴")
//    public ResponseEntity<List<Friend>> getFriendList(@PathVariable Long userID) {
//        List<Friend> friends = friendRepository.findFriendsById(userID);
//        for(Friend friend:friends){
//            log.info("Friend ID: {}", friend.getId());
//            log.info("User ID 1: {}", friend.getUserid1());
//            log.info("User ID 2: {}", friend.getUserid2());
//        }
//        return new ResponseEntity<List<Friend>>(friends, HttpStatus.OK);
//    }

    @DeleteMapping("/{friendID}")
    @Operation(summary = "친구 삭제", description = "친구 삭제하기")
    public ResponseEntity<String> deleteFriend(@PathVariable Long friendID){
        friendRepository.deleteById(friendID);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/accept/{friendshipId}")
    @Operation(summary = "친구 수락",description = "친구 수락하기")
    @ResponseStatus(HttpStatus.OK)
    public String approveFriendship (@Valid @PathVariable("friendshipId") Long friendshipId) throws Exception{
        return friendService.approveFriendshipRequest(friendshipId);
    }

    @PostMapping("/{fromid},{toid}")
    @Operation(summary = "친구 요청",description = "친구 요청하기")
    @ResponseStatus(HttpStatus.OK)
    public String sendFriendshipRequest(@Valid @PathVariable("fromid") Long fromId, @PathVariable("toid") Long toId) throws Exception {
        if(!userService.isExistById(toId)) {
            throw new Exception("대상 회원이 존재하지 않습니다");
        }

        return friendService.createFriendship(fromId,toId);
    }

    @GetMapping("/received/{userID}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "받은 요청",description = "받은 요청 보기")
    public ResponseEntity<?> getWaitingFriendInfo(@PathVariable Long userID) throws Exception {
        return friendService.getWaitingFriendList(userID);
    }

    @GetMapping("/friend/{userID}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "친구 목록",description = "내 친구 목록보기")
    public ResponseEntity<?> getAcceptFriendInfo(@PathVariable Long userID) throws Exception {
        return friendService.getAcceptFriendList(userID);
    }



}
