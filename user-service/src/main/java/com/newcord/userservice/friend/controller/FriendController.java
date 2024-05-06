package com.newcord.userservice.friend.controller;
import com.newcord.userservice.friend.domain.Friend;
import com.newcord.userservice.friend.repository.FriendRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@Slf4j
@Tag(name = "Friend", description = "친구 API")
public class FriendController {

    @Autowired
    private final FriendRepository friendRepository;

    public FriendController(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @GetMapping("/api/{userID}/friends")
    @Operation(summary = "친구 목록 조회", description = "유저의 친구 목록을 불러옴")
    public ResponseEntity<List<Friend>> getFriendList(@PathVariable Long userID) {
        List<Friend> friends = friendRepository.findFriendsById(userID);
        for(Friend friend:friends){
            log.info("Friend ID: {}", friend.getId());
            log.info("User ID 1: {}", friend.getUserid1());
            log.info("User ID 2: {}", friend.getUserid2());
        }
        return new ResponseEntity<List<Friend>>(friends, HttpStatus.OK);
    }

    @DeleteMapping("/api/firends/{friendID}")
    @Operation(summary = "친구 삭제", description = "친구 삭제하기")
    public ResponseEntity<String> deleteFriend(@PathVariable Long friendID){
        friendRepository.deleteById(friendID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/friends/accept/{friendID}")
    @Operation(summary = "친구 수락",description = "친구 수락하기")
    public ResponseEntity<String> acceptFriend(@PathVariable Long friendID){
        friendRepository.acceptFriend(friendID);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
