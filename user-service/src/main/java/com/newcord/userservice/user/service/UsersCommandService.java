package com.newcord.userservice.user.service;

import com.newcord.userservice.user.dto.UsersRequest.UsersRequestDTO;
import com.newcord.userservice.user.dto.UsersResponse.UsersResponseDTO;

public interface UsersCommandService {
    UsersResponseDTO updateUserInfo(UsersRequestDTO usersRequestDTO); // 사용자 정보 업데이트
}
