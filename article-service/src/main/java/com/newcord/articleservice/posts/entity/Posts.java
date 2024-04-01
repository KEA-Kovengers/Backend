package com.newcord.articleservice.posts.entity;

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
public class Posts implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String thumbnail;
    private String title;
    private String body;
    private String status;
    private Integer views;

    @Builder.Default
    private Timestamp created_at = new Timestamp(System.currentTimeMillis());

    @Builder.Default
    private Timestamp updated_at = new Timestamp(System.currentTimeMillis());
}
