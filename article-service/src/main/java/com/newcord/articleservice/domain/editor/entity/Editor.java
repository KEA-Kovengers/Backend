package com.newcord.articleservice.domain.editor.entity;

import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.global.common.BaseJPATimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
    * 게시글 편집자 엔티티
    * 게시글 편집자 정보를 담고 있는 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Editor extends BaseJPATimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userID;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts post;

}
