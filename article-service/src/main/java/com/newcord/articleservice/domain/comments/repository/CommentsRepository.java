package com.newcord.articleservice.domain.comments.repository;

import com.newcord.articleservice.domain.comments.entity.Comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CommentsRepository extends JpaRepository<Comments,Long> {

    @Query("SELECT c FROM Comments c WHERE c.post_id =:postID")
    List<Comments> findByPost_id(Long postID);
}
