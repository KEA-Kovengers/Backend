package com.newcord.userservice.folder.domain;

import com.newcord.userservice.BaseTimeEntity;
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
    @GeneratedValue
    private Long id; // 폴더 아이디

    @Column(nullable = false)
    private Long user_id; // 유저 아이디

    @Column(nullable = false)
    private String folderName; // 폴더명

    //FK
    @OneToMany(mappedBy = "folder")
    private List<FolderPost> folderPosts;
}
