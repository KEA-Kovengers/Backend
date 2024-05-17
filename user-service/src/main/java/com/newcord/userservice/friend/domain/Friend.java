//package com.newcord.userservice.friend.domain;
//
//import com.newcord.userservice.friend.status.FriendshipStatus;
//import com.newcord.userservice.global.common.BaseTimeEntity;
//import com.newcord.userservice.user.domain.Users;
//import jakarta.persistence.*;
//import lombok.Builder;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "friend")
//@Getter
//@EntityListeners(AuditingEntityListener.class)
//@Builder
////@IdClass(FriendId.class)
//public class Friend extends BaseTimeEntity {
//
//
//    //    @EmbeddedId
////    private FriendId id;
//
////    @Id
////    @ManyToOne
////    @JoinColumn(name = "user_id1", referencedColumnName = "id", insertable = false, updatable = false)
////    private Users userId1;
////
////    @Id
////    @ManyToOne
////    @JoinColumn(name = "user_id2", referencedColumnName = "id", insertable = false, updatable = false)
////    private Users userId2;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private Users users;
//
////    @Column(name = "userId1")
////    private Long userid1;
////
////
////    @Column(name = "userId2")
////    private Long userid2;
//    private Long userID;
//    private Long friendID;
//    private FriendshipStatus status;
//    private boolean isFrom;
//
//    private Long counterpartId;
//
//    public Friend(Long id, Users users, Long userID, Long friendID, FriendshipStatus status, boolean isFrom, Long counterpartId) {
//        this.id = id;
//        this.users = users;
//        this.userID = userID;
//        this.friendID = friendID;
//        this.status = status;
//        this.isFrom = isFrom;
//        this.counterpartId = counterpartId;
//    }
//
//    public void acceptFriendshipRequest() {
//        status = FriendshipStatus.ACCEPT;
//
//    }
//    public void setcountpartid(Long id){
//        counterpartId=id;
//    }
////
////    @Column()
////    private String status;
//}
//
////    @EmbeddedId
////    private FriendId id;
//
//
////    @CreatedDate
////    @Column(name = "created_at")
////    private LocalDateTime created;
package com.newcord.userservice.friend.domain;

import com.newcord.userservice.friend.status.FriendshipStatus;
import com.newcord.userservice.global.common.BaseTimeEntity;
import com.newcord.userservice.user.domain.Users;
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
