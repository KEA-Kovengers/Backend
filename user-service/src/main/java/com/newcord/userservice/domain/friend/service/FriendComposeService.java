package com.newcord.userservice.domain.friend.service;

import com.newcord.userservice.domain.friend.dto.FriendResponse.*;

import java.util.List;

public interface FriendComposeService {
    List<FriendInfoResponseDTO> getAcceptFriendList(Long userid);

    List<FriendInfoResponseDTO> getWaitingFriendList(Long userid);
}

