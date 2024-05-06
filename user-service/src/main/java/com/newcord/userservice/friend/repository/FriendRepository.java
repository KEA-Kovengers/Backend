package com.newcord.userservice.friend.repository;

import com.newcord.userservice.friend.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("select u from Friend u where u.userid1 = :userID or u.userid2 = :userID and u.status = 'friend'")
    List<Friend> findFriendsById(@Param("userID") Long userID);

    @Query("UPDATE Friend f SET f.status = 'friend' where f.userid2 = :userID or f.userid1 =:userID")
    void acceptFriend(@Param("userID") Long userID);
}