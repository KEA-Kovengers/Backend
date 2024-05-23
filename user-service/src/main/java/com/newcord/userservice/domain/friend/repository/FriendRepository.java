package com.newcord.userservice.domain.friend.repository;

import com.newcord.userservice.domain.friend.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findAllByFriendIDAndUserID(Long UserID, Long FriendID);
}