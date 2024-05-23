package com.newcord.userservice.domain.friend.service;

import com.newcord.userservice.domain.friend.domain.Friend;
import com.newcord.userservice.domain.friend.dto.FriendResponse;
import com.newcord.userservice.domain.user.domain.Users;

import java.util.List;

public interface FriendQueryService {

    //받은 친구 요청 조회


    List<FriendResponse.FriendResponseDTO> getWaitingFriendList(Long userid);


    List<FriendResponse.FriendResponseDTO> getAcceptFriendList(Long userid);

    Users getUsersById(Long id);

    Friend getFriendRequest(Long id);

    //친구 목록 조회
}
