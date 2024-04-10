package com.newcord.articleservice.domain.collaboration.entity;


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
public class Collaboration extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer user_id;
    private Integer post_id;
}
