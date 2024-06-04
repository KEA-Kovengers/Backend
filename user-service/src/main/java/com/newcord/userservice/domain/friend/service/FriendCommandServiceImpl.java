package com.newcord.userservice.domain.friend.service;

import com.newcord.userservice.domain.friend.domain.Friend;
import com.newcord.userservice.domain.friend.dto.FriendRequest.*;
import com.newcord.userservice.domain.friend.dto.FriendResponse.*;
import com.newcord.userservice.domain.friend.repository.FriendRepository;
import com.newcord.userservice.domain.friend.status.FriendshipStatus;
import com.newcord.userservice.global.common.exception.ApiException;
import com.newcord.userservice.global.common.response.code.status.ErrorStatus;
import com.newcord.userservice.domain.user.domain.Users;
import com.newcord.userservice.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FriendCommandServiceImpl implements FriendCommandService{

    private final UsersRepository usersRepository;
    private final FriendRepository friendRepository;

    private final WebClient webClient = WebClient.builder().build();


    @Override
    public FriendResponseDTO createFriendship(Long fromID, CreateFriendRequestDTO createfriendRequestDTO){
        Long toID=createfriendRequestDTO.getToID();
        // 유저 정보를 모두 가져옴
        Users fromUser = usersRepository.findById(fromID).orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
        Users toUser = usersRepository.findById(toID).orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));

        List<Friend> frinedlist=friendRepository.findAllByFriendIDAndUserID(fromID,toID);

        for (Friend x:frinedlist){
            if(x.getStatus()==FriendshipStatus.WAITING){
                throw new ApiException(ErrorStatus._ALREADY_REQUEST);
            }
            if(x.getStatus()==FriendshipStatus.ACCEPT){
                throw new ApiException(ErrorStatus._ALREADY_FRIEND);
            }
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

        // 알림 API 요청
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("user_id", toID);
        requestBody.put("from_id", fromID);
        requestBody.put("type", "FRIEND_REQUEST");
        webClient.post()
                .uri("http://newcord.kro.kr/notices/addNotice")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

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

        // 알림 API 요청
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("user_id", friendship.getFriendID());
        requestBody.put("from_id", friendship.getUserID());
        requestBody.put("type", "FREIEND_RESPONSE");
        webClient.post()
                .uri("http://newcord.kro.kr/notices/addNotice")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Void.class)
                .block();


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
