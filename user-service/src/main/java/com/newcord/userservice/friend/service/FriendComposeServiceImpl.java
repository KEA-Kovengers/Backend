package com.newcord.userservice.friend.service;


import com.newcord.userservice.friend.domain.Friend;

import com.newcord.userservice.friend.dto.FriendResponse.*;
import com.newcord.userservice.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendComposeServiceImpl implements FriendComposeService{
    private final FriendCommandService friendCommandService;
    private final FriendQueryService friendQueryService;

    @Override
    public Friend requestFriendship(Long fromID, Long toID){
        //유저가 존재하는지 확인
        friendQueryService.getUsersById(fromID);
        friendQueryService.getUsersById(toID);

        return friendCommandService.createFriendship(fromID,toID);
    }

    @Override
    public Friend approveFriendshipRequest(Long friendshipId){
        Friend friend=friendQueryService.getFriendRequest(friendshipId);
        friendQueryService.getFriendRequest(friend.getCounterpartId());

        return friendCommandService.approveFriendshipRequest(friendshipId);
    }

    @Override
    public Friend rejectFriendshipRequest(Long friendshipId){
        Friend friend=friendQueryService.getFriendRequest(friendshipId);
        friendQueryService.getFriendRequest(friend.getCounterpartId());

        return friendCommandService.rejectFriendshipRequest(friendshipId);
    }

    @Override
    public Friend deleteFriendship(Long friendshipId){
        Friend friend=friendQueryService.getFriendRequest(friendshipId);
        friendQueryService.getFriendRequest(friend.getCounterpartId());

        return friendCommandService.deleteFriendship(friendshipId);
    }

    @Override
    public List<FriendResponseDTO> getWaitingFriendList(Long id){
        Users users=friendQueryService.getUsersById(id);

        return friendQueryService.getWaitingFriendList(id);

    }

    @Override
    public List<FriendResponseDTO> getAcceptFriendList(Long id){
        Users users=friendQueryService.getUsersById(id);
        return friendQueryService.getAcceptFriendList(id);
    }
}
