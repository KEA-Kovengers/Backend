package com.newcord.userservice.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FriendRequest {

    @Builder
    @Getter
    public static class CreateFriendRequestDTO{
        private Long fromID;
        private Long toID;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FriendRequestDTO{
        private Long id;
    }
}
