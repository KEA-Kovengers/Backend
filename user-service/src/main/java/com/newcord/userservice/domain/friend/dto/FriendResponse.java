package com.newcord.userservice.domain.friend.dto;

import com.newcord.userservice.domain.friend.status.FriendshipStatus;
import lombok.Builder;
import lombok.Getter;

public class FriendResponse {

    @Builder
    @Getter
    public static class FriendResponseDTO{
        private Long friendshipID;
        private Long userID;
        private Long friendID;
        private FriendshipStatus status;
        private boolean isFrom;
    }

}
