package com.newcord.userservice.domain.user.service;

import com.newcord.userservice.domain.user.repository.UsersRepository;
import com.newcord.userservice.global.common.exception.ApiException;
import com.newcord.userservice.global.common.response.code.status.ErrorStatus;
import com.newcord.userservice.domain.user.domain.Users;
import com.newcord.userservice.domain.user.dto.UsersRequest.UsersNameRequestDTO;
import com.newcord.userservice.domain.user.dto.UsersRequest.UsersRequestDTO;
import com.newcord.userservice.domain.user.dto.UsersResponse.UsersResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsersCommandServiceImpl implements UsersCommandService{
    public final UsersRepository usersRepository;

    @Override
    public UsersResponseDTO updateUserInfo(Long userID, UsersRequestDTO usersRequestDTO){
        Users user = usersRepository.findById(userID).orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));

        usersRepository.updateUser(userID, usersRequestDTO.getNickName(),
                usersRequestDTO.getBlogName(),usersRequestDTO.getBio(),usersRequestDTO.getProfileImg());

        return UsersResponseDTO.builder()
                .nickName(usersRequestDTO.getNickName())
                .blogName(usersRequestDTO.getBlogName())
                .bio(usersRequestDTO.getBio())
                .profileImg(usersRequestDTO.getProfileImg())
                .build();
    }

    @Override
    public UsersResponseDTO updateUserImg(Long userID, String profileImg){
        Users user = usersRepository.findById(userID).orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));

        usersRepository.updateProfileImg(userID, profileImg);

        return UsersResponseDTO.builder()
                .nickName(user.getNickName())
                .blogName(user.getBlogName())
                .bio(user.getBio())
                .profileImg(profileImg)
                .build();
    }

    @Override
    public UsersResponseDTO updateUserName(Long userID, UsersNameRequestDTO usersNameRequestDTO){
        Users user = usersRepository.findById(userID).orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));

        String nickName = usersNameRequestDTO.getNickName();
        String blogName = usersNameRequestDTO.getBlogName();

        usersRepository.updateUserNameAndBlogName(userID, nickName, blogName);

        return UsersResponseDTO.builder()
                .nickName(nickName)
                .blogName(blogName)
                .bio(user.getBio())
                .profileImg(user.getProfileImg())
                .build();
    }

}
