package com.newcord.articleservice.domain.likes.repository;

import com.newcord.articleservice.domain.likes.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Likes,Long> {
    //List<Likes> findAllByUser_id(Long User_id);

    @Query("SELECT c FROM Likes c WHERE c.user_id =:userID")
    List<Likes> findAllByUser_id(Long userID);

    @Query("SELECT c FROM Likes c WHERE c.post_id =:postID")
    List<Likes> findAllByPost_id(Long postID);

}
