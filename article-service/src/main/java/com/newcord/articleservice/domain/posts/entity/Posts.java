package com.newcord.articleservice.domain.posts.entity;

import com.newcord.articleservice.global.common.BaseJPATimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Posts extends BaseJPATimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String thumbnail;
    private String title;
    private String body;
    private String status;
    private Integer views;
}
