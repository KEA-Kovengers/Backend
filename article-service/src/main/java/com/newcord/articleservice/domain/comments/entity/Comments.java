package com.newcord.articleservice.domain.comments.entity;

import com.newcord.articleservice.global.common.BaseJPATimeEntity;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.*;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comments extends BaseJPATimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //대댓글일 경우 부모의 id가 있음
    //그냥 댓글일 경우에는 id
    private Long comment_id;
    @Column(name = "post_id")
    private Long postId;
    private Long user_id;
    private String body;
    @ColumnDefault("FALSE")
    @Column
    private Boolean isDeleted;
    // comment_id로 정렬
    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
    // Constructors, Getters, and Setters
    @PostPersist
    private void assignCommentId() {
        if (this.comment_id == null) {
            this.comment_id = this.id;
        }
    }


}

