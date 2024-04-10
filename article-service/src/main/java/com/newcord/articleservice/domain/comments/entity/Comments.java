package com.newcord.articleservice.domain.comments.entity;

import com.newcord.articleservice.global.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comments extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer comment_id;
    private Integer post_id;
    private Integer user_id;
    private String body;

    // Constructors, Getters, and Setters
}

