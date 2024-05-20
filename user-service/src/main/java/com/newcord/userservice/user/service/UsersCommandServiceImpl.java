package com.newcord.userservice.user.service;

import com.newcord.userservice.global.common.exception.ApiException;
import com.newcord.userservice.global.common.response.code.status.ErrorStatus;
import com.newcord.userservice.user.domain.Users;
import com.newcord.userservice.user.dto.UsersRequest.UsersRequestDTO;
import com.newcord.userservice.user.dto.UsersResponse.UsersResponseDTO;
import com.newcord.userservice.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersCommandServiceImpl implements UsersCommandService{
    public final UsersRepository usersRepository;

    @Override
    public UsersResponseDTO updateUserInfo(UsersRequestDTO usersRequestDTO){
        Long id = usersRequestDTO.getId();
        Users user = usersRepository.findById(id).orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));

        Users updatedUser = usersRequestDTO.toEntity(usersRequestDTO);
        Users savedUser = usersRepository.save(updatedUser);
        return UsersResponseDTO.builder()
                .nickName(savedUser.getNickName())
                .blogName(savedUser.getBlogName())
                .bio(savedUser.getBio())
                .profileImg(savedUser.getProfileImg())
                .build();
    }

}
