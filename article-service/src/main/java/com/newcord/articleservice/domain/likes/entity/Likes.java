package com.newcord.articleservice.domain.likes.entity;

import com.newcord.articleservice.global.common.BaseJPATimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Likes extends BaseJPATimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer post_id;
    private Integer user_id;
}
