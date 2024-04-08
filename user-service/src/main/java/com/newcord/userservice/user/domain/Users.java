package com.newcord.userservice.user.domain;

import com.newcord.userservice.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Entity
@Table(name="USERS")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
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

    @Column(length = 300)
    private String profileImg;

    @Column(length = 500)
    private String refreshToken;

}