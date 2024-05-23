package com.newcord.userservice.domain.user.domain;

import com.newcord.userservice.domain.friend.domain.Friend;
import com.newcord.userservice.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="USERS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Users extends BaseTimeEntity {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100,unique = true)
    private String nickName;
    @Column(length = 100)
    private String blogName;

    @Column()
    private String role;
    @Column(length = 100)
    private String bio;

    @OneToMany(mappedBy = "users")
    @Column()
    private List<Friend> friendList=new ArrayList<>();

    @Column(length = 300)
    private String profileImg;

    @Column(length = 500)
    private String kakaoToken;

    @Column(length = 500)
    private String refreshToken;

    public void setKakaoToken(String kakaoToken) {
        this.kakaoToken = kakaoToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}