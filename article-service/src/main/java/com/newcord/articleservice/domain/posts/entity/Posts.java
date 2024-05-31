package com.newcord.articleservice.domain.posts.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.newcord.articleservice.domain.hashtags.entity.Hashtags;
import com.newcord.articleservice.domain.posts.dto.PostRequest.PostUpdateRequestDTO;
import com.newcord.articleservice.domain.posts.enums.PostStatus;
import com.newcord.articleservice.global.common.BaseJPATimeEntity;
import jakarta.persistence.*;

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
    @Default
    private PostStatus status = PostStatus.EDIT;

    @Default
    private Long views = 0L;

    @ManyToMany
    @JsonManagedReference
    @JoinTable(
        name = "post_hashtags",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    @Default
    private Set<Hashtags> hashtags = new HashSet<>();

    public void updateHashtagList(List<Hashtags> hashtags){
        this.hashtags = new HashSet<>(hashtags);
    }
    public void updateHashtagList(Set<Hashtags> hashtags){
        this.hashtags = hashtags;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void updateByDTO(PostUpdateRequestDTO updateRequestDTO){
        if (updateRequestDTO.getThumbnail() != null) this.thumbnail = updateRequestDTO.getThumbnail();
        if (updateRequestDTO.getTitle() != null) this.title = updateRequestDTO.getTitle();
        if (updateRequestDTO.getStatus() != null) this.status = updateRequestDTO.getStatus();
        super.update();
    }
}
