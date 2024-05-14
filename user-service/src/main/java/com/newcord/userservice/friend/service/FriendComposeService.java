package com.newcord.userservice.friend.service;

import com.newcord.userservice.friend.domain.Friend;
import com.newcord.userservice.friend.dto.FriendResponse.*;

import java.util.List;

public interface FriendComposeService {

    Friend requestFriendship(Long fromID, Long toID);

    Friend approveFriendshipRequest(Long friendshipId);

    Friend rejectFriendshipRequest(Long friendshipId);

    Friend deleteFriendship(Long friendshipId);

    List<FriendResponseDTO> getWaitingFriendList(Long id);

    List<FriendResponseDTO> getAcceptFriendList(Long id);
}
