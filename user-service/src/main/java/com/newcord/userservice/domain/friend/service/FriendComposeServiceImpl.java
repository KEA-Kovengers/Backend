package com.newcord.userservice.domain.friend.service;

import com.newcord.userservice.domain.friend.dto.FriendResponse.*;
import com.newcord.userservice.domain.user.dto.UsersResponse.*;
import com.newcord.userservice.domain.user.service.UsersQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FriendComposeServiceImpl implements FriendComposeService{
    private final FriendCommandService friendCommandService;
    private final FriendQueryService friendQueryService;
    private final UsersQueryService usersQueryService;

    @Override
    public List<FriendInfoResponseDTO> getAcceptFriendList(Long userid){
        List<FriendResponseDTO> friendResponseDTOS=friendQueryService.getAcceptFriendList(userid);
        List<FriendInfoResponseDTO> result=new ArrayList<>();
        for (FriendResponseDTO friendInfoResponseDTO : friendResponseDTOS){
            UsersResponseDTO usersResponseDTO=usersQueryService.getUserInfo(friendInfoResponseDTO.getFriendID());
            FriendInfoResponseDTO dto=FriendInfoResponseDTO.builder()
                    .friendshipID(friendInfoResponseDTO.getFriendshipID())
                    .userID(userid)
                    .friendID(friendInfoResponseDTO.getUserID())
                    .status(friendInfoResponseDTO.getStatus())
                    .isFrom(friendInfoResponseDTO.isFrom())
                    .nickName(usersResponseDTO.getNickName())
                    .blogName(usersResponseDTO.getBlogName())
                    .bio(usersResponseDTO.getBio())
                    .profileImg(usersResponseDTO.getProfileImg())
                    .build();
            result.add(dto);
        }
        return result;
}

@Override
    public List<FriendInfoResponseDTO> getWaitingFriendList(Long userid){
    List<FriendResponseDTO> friendResponseDTOS=friendQueryService.getWaitingFriendList(userid);
    List<FriendInfoResponseDTO> result=new ArrayList<>();
    for (FriendResponseDTO friendInfoResponseDTO : friendResponseDTOS){
        UsersResponseDTO usersResponseDTO=usersQueryService.getUserInfo(friendInfoResponseDTO.getFriendID());
        FriendInfoResponseDTO dto=FriendInfoResponseDTO.builder()
                .friendshipID(friendInfoResponseDTO.getFriendshipID())
                .userID(userid)
                .friendID(friendInfoResponseDTO.getUserID())
                .status(friendInfoResponseDTO.getStatus())
                .isFrom(friendInfoResponseDTO.isFrom())
                .nickName(usersResponseDTO.getNickName())
                .blogName(usersResponseDTO.getBlogName())
                .bio(usersResponseDTO.getBio())
                .profileImg(usersResponseDTO.getProfileImg())
                .build();
        result.add(dto);
    }
    return result;
}


}
