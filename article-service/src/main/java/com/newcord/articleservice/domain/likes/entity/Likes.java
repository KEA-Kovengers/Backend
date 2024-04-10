package com.newcord.articleservice.domain.likes.entity;

import com.newcord.articleservice.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Likes extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer post_id;
    private Integer user_id;
}
