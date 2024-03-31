package com.newcord.userservice.folder.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class FolderPostId implements Serializable {
    private Long folder_id;// 폴더 아이디
    private Long post_id;// 게시글 아이디
}

