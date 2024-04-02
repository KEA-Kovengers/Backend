package com.newcord.userservice.user.domain;

import com.newcord.userservice.BaseTimeEntity;
import com.newcord.userservice.folder.domain.FolderPost;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;


@Entity
@Table(name="USERS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100,unique = true)
    private String nickName;
    @Column(length = 100)
    private String blogName;

    @Column()
    private String role;
    @Column(length = 100)
    private String bio;

    @Column(length = 300)
    private String profileImg;
//
//    @OneToMany(mappedBy = "user")
//    private List<Friend> friendList;

//    @CreatedDate
//    @Column(name="created_at")
//    private LocalDateTime created;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updated;
//


}