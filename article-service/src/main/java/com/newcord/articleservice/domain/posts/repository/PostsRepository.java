package com.newcord.articleservice.domain.posts.repository;

import com.newcord.articleservice.domain.posts.entity.Posts;
import com.newcord.articleservice.domain.posts.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
//    @Query("SELECT h FROM Posts h WHERE h.hashtags = :tagName")
//    List<Posts> findAllByHashtags(Long id);

    @Query("SELECT p FROM Posts p JOIN p.hashtags h WHERE h.tagName = :hashtagName and p.status =:postStatus")
    Page<Posts> findPostsByHashtagName(String hashtagName, PostStatus postStatus,Pageable pageable);

    @Query("SELECT p FROM Posts p WHERE p.status =:status")
    Page<Posts> findPosts(PostStatus status,Pageable pageable);
}

