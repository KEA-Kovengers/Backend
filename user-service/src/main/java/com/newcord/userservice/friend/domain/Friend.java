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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column()
    private FriendshipStatus status;

    @Column()
    private boolean isFrom;

    @Column()
    private Long counterpartId;



    public void acceptFriendshipRequest() {
        status = FriendshipStatus.ACCEPT;
    }

}
