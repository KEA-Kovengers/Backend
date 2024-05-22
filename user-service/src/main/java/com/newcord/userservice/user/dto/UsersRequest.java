package com.newcord.userservice.user.dto;

import com.newcord.userservice.user.domain.Users;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

public class UsersRequest {

    @Builder
    @Getter
    public static class UsersRequestDTO {
        private Long id;
        private String nickName;
        private String blogName;
        private String bio;
        private String profileImg;

        public Users toEntity(UsersRequestDTO dto){
            return Users.builder()
                    .id(dto.getId())
                    .nickName(dto.getNickName())
                    .blogName(dto.getBlogName())
                    .bio(dto.getBio())
                    .profileImg(dto.getProfileImg())
                    .build();
        }

    }

}
