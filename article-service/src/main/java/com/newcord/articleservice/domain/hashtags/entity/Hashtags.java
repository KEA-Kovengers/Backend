package com.newcord.articleservice.domain.hashtags.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.global.common.BaseJPATimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.*;
import lombok.Builder.Default;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hashtags extends BaseJPATimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;
    private String tagName;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Hashtags hashtags = (Hashtags) obj;
        return id.equals(hashtags.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

