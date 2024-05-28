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

    @Builder
    @Getter
    public static class FriendInfoResponseDTO{
        private Long friendshipID;
        private Long userID;
        private FriendshipStatus status;
        private boolean isFrom;
        private String nickName;
        private String blogName;
        private String bio;
        private String profileImg;
    }

}
