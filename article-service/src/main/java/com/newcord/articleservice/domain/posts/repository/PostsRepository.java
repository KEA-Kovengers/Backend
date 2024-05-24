package com.newcord.articleservice.domain.posts.repository;

import com.newcord.articleservice.domain.posts.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
//    @Query("SELECT h FROM Posts h WHERE h.hashtags = :tagName")
//    List<Posts> findAllByHashtags(Long id);

    @Query("SELECT p FROM Posts p JOIN p.hashtags h WHERE h.tagName = :hashtagName")
    List<Posts> findPostsByHashtagName(String hashtagName);
}

