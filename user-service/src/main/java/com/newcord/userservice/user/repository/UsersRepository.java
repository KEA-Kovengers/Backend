package com.newcord.userservice.user.repository;

import com.newcord.userservice.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
   Users findUsersByNickName(String nickName);
}

