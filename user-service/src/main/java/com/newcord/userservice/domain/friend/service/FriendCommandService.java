package com.newcord.userservice.domain.friend.service;


import com.newcord.userservice.domain.friend.dto.FriendRequest.*;
import com.newcord.userservice.domain.friend.dto.FriendResponse.*;

public interface FriendCommandService {



    FriendResponseDTO createFriendship(Long fromID, CreateFriendRequestDTO createfriendRequestDTO);



    FriendResponseDTO approveFriendshipRequest(FriendRequestDTO friendRequestDTO);


    FriendResponseDTO rejectFriendshipRequest(FriendRequestDTO friendRequestDTO);


    FriendResponseDTO deleteFriendship(FriendRequestDTO friendRequestDTO);
}
