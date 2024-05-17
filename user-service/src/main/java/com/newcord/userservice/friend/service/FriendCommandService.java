package com.newcord.userservice.friend.service;


import com.newcord.userservice.friend.dto.FriendRequest.*;
import com.newcord.userservice.friend.dto.FriendResponse.*;

public interface FriendCommandService {



    FriendResponseDTO createFriendship(CreateFriendRequestDTO createfriendRequestDTO);



    FriendResponseDTO approveFriendshipRequest(FriendRequestDTO friendRequestDTO);


    FriendResponseDTO rejectFriendshipRequest(FriendRequestDTO friendRequestDTO);


    FriendResponseDTO deleteFriendship(FriendRequestDTO friendRequestDTO);
}
