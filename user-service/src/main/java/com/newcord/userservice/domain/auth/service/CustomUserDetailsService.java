package com.newcord.userservice.domain.auth.service;

import com.newcord.userservice.domain.user.domain.Users;
import com.newcord.userservice.domain.user.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j; // Import from Lombok
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Long id = Long.parseLong(username);
        Users user = userRepository.findUsersById(id);
        if (user == null) {
            log.error("User not found with username: {}", id);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return User.withUsername(user.getId().toString())
                .password("")
                .authorities("USER")
                .build();
    }
}
