package com.newcord.userservice.domain.user.dto;

import com.newcord.userservice.domain.user.domain.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UsersRequest {

    @Builder
    @Getter
    public static class UsersRequestDTO {
        private String nickName;
        private String blogName;
        private String bio;
        private String profileImg;

        public Users toEntity(Long userID, UsersRequestDTO dto){
            return Users.builder()
                    .id(userID)
                    .nickName(dto.getNickName())
                    .blogName(dto.getBlogName())
                    .bio(dto.getBio())
                    .profileImg(dto.getProfileImg())
                    .build();
        }

    }

    @Builder
    @Getter
    @Setter
    public static class UsersNameRequestDTO{
        private String nickName;
        private String blogName;
    }

}
