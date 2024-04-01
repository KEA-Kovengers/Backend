package com.newcord.articleservice.collaboration.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Collaboration implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer user_id;
    private Integer post_id;

    @Builder.Default
    private Timestamp created_at = new Timestamp(System.currentTimeMillis());
    @Builder.Default
    private Timestamp updated_at = new Timestamp(System.currentTimeMillis());
}
