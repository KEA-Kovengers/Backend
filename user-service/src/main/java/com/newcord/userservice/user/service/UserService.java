package com.newcord.userservice.user.service;

import com.newcord.userservice.user.domain.Users;
import com.newcord.userservice.user.repository.UsersRepository;
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
