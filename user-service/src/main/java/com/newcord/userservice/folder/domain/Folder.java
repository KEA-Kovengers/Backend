package com.newcord.userservice.folder.domain;

import com.newcord.userservice.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Folder extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 폴더 아이디

    @Column()
    private Long user_id; // 유저 아이디

    @Column()
    private String folderName; // 폴더명
}
