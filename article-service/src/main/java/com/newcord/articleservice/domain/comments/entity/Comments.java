package com.newcord.articleservice.domain.comments.entity;

import com.newcord.articleservice.global.common.BaseJPATimeEntity;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;


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
    //그냥 댓글일 경우에는 null
    private Long comment_id;
    private Long post_id;
    private Long user_id;
    private String body;
    @ColumnDefault("FALSE")
    @Column
    private Boolean isDeleted;

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    // Constructors, Getters, and Setters
}

