package com.newcord.articleservice.domain.posts_hashtag.entity;

import com.newcord.articleservice.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostsHashtag extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer post_id;
    private Integer hashtag_id;
}
