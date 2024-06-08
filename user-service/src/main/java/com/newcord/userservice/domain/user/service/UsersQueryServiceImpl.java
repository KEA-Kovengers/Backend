package com.newcord.userservice.domain.user.service;

import com.newcord.userservice.domain.user.repository.UsersRepository;
import com.newcord.userservice.global.common.exception.ApiException;
import com.newcord.userservice.global.common.response.code.status.ErrorStatus;
import com.newcord.userservice.domain.user.domain.Users;
import com.newcord.userservice.domain.user.dto.UsersResponse.UsersResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersQueryServiceImpl implements UsersQueryService {
    private final UsersRepository usersRepository;

    @Override
    @Cacheable(value = "userInfo", key = "#id")
    public UsersResponseDTO getUserInfo(Long id) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
        return UsersResponseDTO.builder()
                .nickName(user.getNickName())
                .blogName(user.getBlogName())
                .bio(user.getBio())
                .profileImg(user.getProfileImg())
                .build();
    }

}
