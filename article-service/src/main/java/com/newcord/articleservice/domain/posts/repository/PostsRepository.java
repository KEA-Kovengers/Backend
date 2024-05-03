package com.newcord.articleservice.domain.posts.repository;

import com.newcord.articleservice.domain.posts.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
}
