package com.newcord.userservice.user.domain;

import com.newcord.userservice.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "friend")
@EntityListeners(AuditingEntityListener.class)
//@IdClass(FriendId.class)
public class Friend extends BaseTimeEntity {

//    @EmbeddedId
//    private FriendId id;

//    @Id
//    @ManyToOne
//    @JoinColumn(name = "user_id1", referencedColumnName = "id", insertable = false, updatable = false)
//    private Users userId1;
//
//    @Id
//    @ManyToOne
//    @JoinColumn(name = "user_id2", referencedColumnName = "id", insertable = false, updatable = false)
//    private Users userId2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId1")
    private Long userid1;


    @Column(name = "userId2")
    private Long userid2;

    @Column()
    private String status;
}

//    @EmbeddedId
//    private FriendId id;


//    @CreatedDate
//    @Column(name = "created_at")
//    private LocalDateTime created;
