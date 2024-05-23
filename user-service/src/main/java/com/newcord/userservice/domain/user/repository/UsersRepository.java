package com.newcord.userservice.domain.user.repository;

import com.newcord.userservice.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
   Users findUsersById(Long id);

   @Modifying
   @Query("UPDATE Users u SET u.nickName = :nickName, u.blogName = :blogName WHERE u.id = :userID")
   void updateUserNameAndBlogName( Long userID, String nickName, String blogName);

   @Modifying
   @Query("UPDATE Users u SET u.profileImg = :profileImg WHERE u.id = :userID")
   void updateProfileImg( Long userID, String profileImg);

   @Modifying
   @Query("UPDATE Users u SET u.nickName = :nickName, u.blogName = :blogName, u.bio = :bio, u.profileImg = :profileImg WHERE u.id = :userID")
   void updateUser(Long userID, String nickName, String blogName, String bio, String profileImg);

}

