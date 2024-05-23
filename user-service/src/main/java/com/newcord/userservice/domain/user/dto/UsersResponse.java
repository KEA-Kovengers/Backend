package com.newcord.userservice.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

public class UsersResponse {

    @Builder
    @Getter
    public static class UsersResponseDTO {
        private String nickName;
        private String blogName;
        private String bio;
        private String profileImg;
    }
}
