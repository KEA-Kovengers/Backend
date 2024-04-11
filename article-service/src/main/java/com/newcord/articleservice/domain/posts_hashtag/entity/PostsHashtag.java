package com.newcord.articleservice.domain.posts_hashtag.entity;

import com.newcord.articleservice.global.common.BaseJPATimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostsHashtag extends BaseJPATimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer post_id;
    private Integer hashtag_id;
}
