package com.newcord.userservice.friend.repository;

import com.newcord.userservice.friend.domain.Friend;
import com.newcord.userservice.friend.status.FriendshipStatus;
import com.newcord.userservice.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {


    List<Friend> findFriendsByFriendIDOrUserIDAndStatus(Long FriendID, Long UserID,FriendshipStatus status);
}