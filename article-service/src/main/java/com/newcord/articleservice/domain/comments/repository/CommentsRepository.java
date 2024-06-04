package com.newcord.articleservice.domain.comments.repository;

import com.newcord.articleservice.domain.comments.entity.Comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CommentsRepository extends JpaRepository<Comments,Long> {

    @Query("SELECT c FROM Comments c WHERE c.postId =:postID")
    List<Comments> findByPostId(Long postID);

    @Query("SELECT c FROM Comments c WHERE c.user_id =:userID")
    List<Comments> findByUser_id(Long userID);
    //List<Comments> findByPostIdOrderByComment_idAsc(Long postId);

    @Query("SELECT c FROM Comments c WHERE c.comment_id = :commentId")
    Optional<Comments> findByCommentId(@Param("commentId") Long commentId);}
