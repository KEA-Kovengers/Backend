package com.newcord.userservice.user.service;

import com.newcord.userservice.user.dto.UsersResponse.UsersResponseDTO;

public interface UsersQueryService {
    UsersResponseDTO getUserInfo(Long id); // 사용자 정보 조회
}
