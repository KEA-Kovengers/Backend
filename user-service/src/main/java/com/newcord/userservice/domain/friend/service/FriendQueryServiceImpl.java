package com.newcord.userservice.domain.friend.service;

import com.newcord.userservice.domain.friend.domain.Friend;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendQueryServiceImpl implements FriendQueryService{

    private final UsersRepository usersRepository;
    private final FriendRepository friendRepository;

    @Override
    public List<FriendResponseDTO> getWaitingFriendList(Long userid){
        Users users = usersRepository.findById(userid).orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
        List<Friend> friendshipList = users.getFriendList();
        log.info(friendshipList.toString());
        // 조회된 결과 객체를 담을 Dto 리스트
        List<FriendResponseDTO> result = new ArrayList<>();
        for (Friend x : friendshipList) {
            // 보낸 요청이 아니고 && 수락 대기중인 요청만 조회
            if (!x.isFrom() && x.getStatus() == FriendshipStatus.WAITING) {
                Users friend = usersRepository.findById(x.getFriendID()).orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
                FriendResponseDTO dto = FriendResponseDTO.builder()
                        .friendshipID(x.getId())
                        .userID(userid)
                        .friendID(friend.getId())
                        .status(x.getStatus())
                        .build();
                result.add(dto);
            }
        }
        // 결과 반환
        return result;
    }

    @Override
    public List<FriendResponseDTO> getAcceptFriendList(Long userid){
        Users users = usersRepository.findById(userid).orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
        List<Friend> friendshipList = users.getFriendList();
        // 조회된 결과 객체를 담을 Dto 리스트
        List<FriendResponseDTO> result = new ArrayList<>();
        for (Friend x : friendshipList) {
            // 보낸 요청이 아니고 && 수락한 요청 조회
            if (x.getStatus() == FriendshipStatus.ACCEPT) {
                Users friend = usersRepository.findById(x.getFriendID()).orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
                FriendResponseDTO dto = FriendResponseDTO.builder()
                        .friendshipID(x.getId())
                        .userID(userid)
                        .friendID(friend.getId())
                        .status(x.getStatus())
                        .build();
                result.add(dto);
            }
        }
        return result;
    }

    @Override
    public Users getUsersById(Long id){
        Users users=usersRepository.findById(id).orElse(null);
        if(users==null){
            throw new ApiException(ErrorStatus._USER_NOT_FOUND);
        }
        return users;
    }

    @Override
    public Friend getFriendRequest(Long id){
        Friend friendship=friendRepository.findById(id).orElse(null);
        if(friendship==null){
            throw new ApiException(ErrorStatus._FRIEND_REQUEST_NOT_FOUND);
        }
        return friendship;
    }

}
