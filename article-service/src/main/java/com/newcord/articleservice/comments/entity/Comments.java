package com.newcord.articleservice.comments.entity;

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
public class Comments implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer comment_id;
    private Integer post_id;
    private Integer user_id;
    private String body;
    @Builder.Default
    private Timestamp created_at = new Timestamp(System.currentTimeMillis());

    @Builder.Default
    private Timestamp updated_at = new Timestamp(System.currentTimeMillis());

    // Constructors, Getters, and Setters
}

