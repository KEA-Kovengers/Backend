package com.newcord.articleservice.domain.posts.entity;

import com.newcord.articleservice.domain.posts.enums.PostStatus;
import com.newcord.articleservice.global.common.BaseJPATimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.Builder.Default;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Posts extends BaseJPATimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    private String thumbnail;
    private String title;
    private String body;
    @Enumerated(EnumType.ORDINAL)
    private PostStatus status;
    @Default
    private Long views = 0L;
}
