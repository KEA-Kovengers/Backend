package com.newcord.articleservice.likes.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Likes implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer post_id;
    private Integer user_id;

    @Builder.Default
    private Timestamp created_at = new Timestamp(System.currentTimeMillis());
}
