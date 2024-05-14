package com.newcord.userservice.friend.service;

import com.newcord.userservice.friend.domain.Friend;
import com.newcord.userservice.friend.dto.FriendResponse.*;
import com.newcord.userservice.user.domain.Users;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface FriendQueryService {

    //받은 친구 요청 조회
    List<FriendResponseDTO> getWaitingFriendList(Long ID);

    List<FriendResponseDTO> getAcceptFriendList(Long id);

    Users getUsersById(Long id);

    Friend getFriendRequest(Long id);

    //친구 목록 조회
}
