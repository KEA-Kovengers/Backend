package com.newcord.articleservice.domain.posts.entity;

import com.newcord.articleservice.domain.hashtags.entity.Hashtags;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostUpdateRequestDTO;
import com.newcord.articleservice.domain.posts.enums.PostStatus;
import com.newcord.articleservice.global.common.BaseJPATimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @ManyToMany
    @JoinTable(
        name = "post_hashtags",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    @Default
    private Set<Hashtags> hashtags = new HashSet<>();

    public void addHashtags(Hashtags hashtag){
        this.hashtags.add(hashtag);
    }

    public void updateByDTO(PostUpdateRequestDTO updateRequestDTO){
        if (updateRequestDTO.getThumbnail() != null) this.thumbnail = updateRequestDTO.getThumbnail();
        if (updateRequestDTO.getTitle() != null) this.title = updateRequestDTO.getTitle();
        if (updateRequestDTO.getStatus() != null) this.status = updateRequestDTO.getStatus();
        super.update();
    }
}
