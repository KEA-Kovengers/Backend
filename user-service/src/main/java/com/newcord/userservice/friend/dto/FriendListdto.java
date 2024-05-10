package com.newcord.userservice.friend.dto;

import com.newcord.userservice.friend.status.FriendshipStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendListdto {
    private Long friendshipId;
    private Long friendId;
    private String friendName;
    private FriendshipStatus status;
    private String imgUrl;


    public FriendListdto(Long friendshipId, Long friendId, String friendName, FriendshipStatus status, String imgUrl) {
        this.friendshipId = friendshipId;
        this.friendId = friendId;
        this.friendName = friendName;
        this.status = status;
        this.imgUrl = imgUrl;
    }
}
