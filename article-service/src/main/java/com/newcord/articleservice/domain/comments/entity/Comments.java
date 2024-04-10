package com.newcord.articleservice.domain.comments.entity;

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
public class Comments extends BaseJPATimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer comment_id;
    private Integer post_id;
    private Integer user_id;
    private String body;

    // Constructors, Getters, and Setters
}

