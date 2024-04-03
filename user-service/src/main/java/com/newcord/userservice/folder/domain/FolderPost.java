package com.newcord.userservice.folder.domain;
import com.newcord.userservice.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="folder_post")
public class FolderPost extends BaseTimeEntity {

//    @EmbeddedId
//    @Column(nullable = false)
//    private FolderPostId folderPostId;
//
//    //FK
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "folder_id",insertable=false, updatable=false)
//    private Folder folder;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long folderid;

    @Column
    private Long postid;


}

