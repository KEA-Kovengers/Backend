package com.newcord.userservice.domain.user.service;

import com.newcord.userservice.domain.user.dto.UsersResponse.UsersResponseDTO;

public interface UsersQueryService {
    UsersResponseDTO getUserInfo(Long id); // 사용자 정보 조회
}
