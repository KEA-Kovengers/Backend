package com.newcord.userservice.friend.service;

import com.newcord.userservice.friend.domain.Friend;
import com.newcord.userservice.friend.dto.FriendResponse.*;

public interface FriendCommandService {
    Friend createFriendship(Long fromID, Long toID);

    Friend approveFriendshipRequest(Long friendshipId);

    Friend rejectFriendshipRequest(Long friendshipId);

    Friend deleteFriendship(Long friendshipId);
}
