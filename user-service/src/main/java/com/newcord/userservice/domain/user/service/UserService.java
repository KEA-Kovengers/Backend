package com.newcord.userservice.domain.user.service;

import com.newcord.userservice.domain.user.repository.UsersRepository;
import com.newcord.userservice.domain.user.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository){
        this.usersRepository=usersRepository;
    }

    public boolean isExistById(Long id){
        Optional<Users> user=usersRepository.findById(id);
        return user.isPresent();
    }
}
