package com.newcord.userservice.domain.friend.domain;

import com.newcord.userservice.domain.friend.status.FriendshipStatus;
import com.newcord.userservice.global.common.BaseTimeEntity;
import com.newcord.userservice.domain.user.domain.Users;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "friend")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @Column()
    private Long userID;

    @Column()
    private Long friendID;

    // enum 타입으로 waiting, accept 두 가지 상태가 존재
    @Column()
    private FriendshipStatus status;

    //보낸 요청인가?
    //요청을 받은 사람은 false
    //요청을 보낸 사람은 true
    @Column()
    private boolean isFrom;

    //상대방의 아이디가 아니라,
    //상대방의 친구db id
    @Column()
    private Long counterpartId;



    public void acceptFriendshipRequest() {
        status = FriendshipStatus.ACCEPT;
    }

}
