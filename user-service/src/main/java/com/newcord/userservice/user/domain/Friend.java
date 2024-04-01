package com.newcord.userservice.user.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "friend")
@EntityListeners(AuditingEntityListener.class)
//@IdClass(FriendId.class)
public class Friend {

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

    @EmbeddedId
    private FriendId id;

    @Column()
    private String status;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime created;
}