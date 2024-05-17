package com.newcord.userservice.friend.service;

import com.newcord.userservice.friend.domain.Friend;
import com.newcord.userservice.friend.dto.FriendRequest.*;
import com.newcord.userservice.friend.dto.FriendResponse.*;
import com.newcord.userservice.friend.repository.FriendRepository;
import com.newcord.userservice.friend.status.FriendshipStatus;
import com.newcord.userservice.global.common.exception.ApiException;
import com.newcord.userservice.global.common.response.code.status.ErrorStatus;
import com.newcord.userservice.user.domain.Users;
import com.newcord.userservice.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FriendCommandServiceImpl implements FriendCommandService{

    private final UsersRepository usersRepository;
    private final FriendRepository friendRepository;

    @Override
    public FriendResponseDTO createFriendship(CreateFriendRequestDTO createfriendRequestDTO){
        Long fromID=createfriendRequestDTO.getFromID();
        Long toID=createfriendRequestDTO.getToID();
        // 유저 정보를 모두 가져옴
        Users fromUser = usersRepository.findById(fromID).orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
        Users toUser = usersRepository.findById(toID).orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
        Optional<Friend> existingFriendship = friendRepository.findAllByFriendIDAndUserIDAndStatus(fromID, toID, FriendshipStatus.WAITING);
        Optional<Friend> alreadyFriendship=friendRepository.findAllByFriendIDAndUserIDAndStatus(fromID,toID,FriendshipStatus.ACCEPT);

        if (existingFriendship.isPresent()) {
            throw new ApiException(ErrorStatus._ALREADY_REQUEST);
        }

        if(alreadyFriendship.isPresent()){
            throw new ApiException(ErrorStatus._ALREADY_FRIEND);
        }

        //요청 보내는 사람 정보 저장
        Friend friendshipFrom=Friend.builder()
                .users(fromUser)
                .userID(fromID)
                .friendID(toID)
                .status(FriendshipStatus.WAITING)
                .isFrom(true)
                .build();

        // 요청을 받는 사람 정보 저장
        Friend friendshipTo = Friend.builder()
                .users(toUser)
                .userID(toID)
                .friendID(fromID)
                .status(FriendshipStatus.WAITING)
                .isFrom(false)
                .build();


        // 저장
        friendRepository.save(friendshipTo);
        friendRepository.save(friendshipFrom);

        // 매칭되는 친구요청의 아이디를 서로 저장
        friendshipTo.setCounterpartId(friendshipFrom.getId());
        friendshipFrom.setCounterpartId(friendshipTo.getId());


        return FriendResponseDTO.builder()
                        .userID(toID)
                                .friendID(fromID)
                                        .status(FriendshipStatus.WAITING)
                                                .isFrom(false).
                build();
    }


    @Override
    public FriendResponseDTO approveFriendshipRequest(FriendRequestDTO friendRequestDTO){
        // 누를 친구 요청과 매칭되는 상대방 친구 요청 둘다 가져옴
        Friend friendship = friendRepository.findById(friendRequestDTO.getId()).orElseThrow(() -> new ApiException(ErrorStatus._FRIEND_REQUEST_NOT_FOUND));
        Friend counterFriendship = friendRepository.findById(friendship.getCounterpartId()).orElseThrow(() -> new ApiException(ErrorStatus._FRIEND_REQUEST_NOT_FOUND));
        log.info(friendship.getId().toString());
        log.info(counterFriendship.getId().toString());
        // 둘다 상태를 ACCEPT로 변경함
        friendship.acceptFriendshipRequest();
        counterFriendship.acceptFriendshipRequest();

        //승인한 친구 정보
        return FriendResponseDTO.builder()
        .friendshipID(counterFriendship.getId()).userID(counterFriendship.getUserID()).friendID(counterFriendship.getFriendID())
                        .status(FriendshipStatus.ACCEPT).isFrom(true).
                build();
    }

    @Override
    public FriendResponseDTO rejectFriendshipRequest(FriendRequestDTO friendRequestDTO) {
        Friend friendship = friendRepository.findById(friendRequestDTO.getId()).orElseThrow(() -> new ApiException(ErrorStatus._FRIEND_REQUEST_NOT_FOUND));
        Friend counterFriendship=friendRepository.findById(friendship.getCounterpartId()).orElseThrow(()->new ApiException(ErrorStatus._FRIEND_REQUEST_NOT_FOUND));

        friendRepository.deleteById(friendship.getId());
        friendRepository.deleteById(counterFriendship.getId());

        //거절한 친구 정보
        return FriendResponseDTO.builder().userID(counterFriendship.getUserID()).friendID(counterFriendship.getFriendID())
                .status(FriendshipStatus.REJECT).isFrom(true).
                build();
    }

    @Override
    public FriendResponseDTO deleteFriendship(FriendRequestDTO friendRequestDTO){
        Friend friendship=friendRepository.findById(friendRequestDTO.getId()).orElseThrow(()->new ApiException(ErrorStatus._FRIEND_REQUEST_NOT_FOUND));
        Friend counterFriendship=friendRepository.findById(friendship.getCounterpartId()).orElseThrow(()->new ApiException(ErrorStatus._FRIEND_REQUEST_NOT_FOUND));

        friendRepository.deleteById(friendship.getId());
        friendRepository.deleteById(counterFriendship.getId());

        //삭제한 친구 정보
        return FriendResponseDTO.builder().userID(counterFriendship.getUserID()).friendID(counterFriendship.getFriendID())
                .status(FriendshipStatus.ACCEPT).isFrom(true).
                build();
    }
}
