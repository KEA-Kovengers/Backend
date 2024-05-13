package com.newcord.articleservice.domain.hashtags.repository;

import com.newcord.articleservice.domain.hashtags.entity.Hashtags;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface HashtagsRepository extends JpaRepository<Hashtags, Long> {
    @Query("SELECT h FROM Hashtags h WHERE h.tagName = :tagName")
    Optional<Hashtags> findByTagName(String tagName);
}
