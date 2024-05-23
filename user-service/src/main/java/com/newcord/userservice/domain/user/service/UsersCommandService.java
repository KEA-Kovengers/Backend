package com.newcord.userservice.domain.user.service;

import com.newcord.userservice.domain.user.dto.UsersRequest.UsersNameRequestDTO;
import com.newcord.userservice.domain.user.dto.UsersRequest.UsersRequestDTO;
import com.newcord.userservice.domain.user.dto.UsersResponse.UsersResponseDTO;

public interface UsersCommandService {
    UsersResponseDTO updateUserInfo(Long userID, UsersRequestDTO usersRequestDTO); // 사용자 정보 업데이트
    UsersResponseDTO updateUserImg(Long userID, String profileImg); // 사용자 프로필 사진 업데이트
    UsersResponseDTO updateUserName(Long userID, UsersNameRequestDTO usersNameRequestDTO); // 사용자 닉네임 및 블로그 이름 업데이트
}
