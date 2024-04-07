package com.newcord.userservice.user.service;

import com.newcord.userservice.user.domain.Users;
import com.newcord.userservice.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository userRepository;

    @Transactional
    public Long createUser(String nickName, String profileImg) {
        Users user = Users.builder()
                .nickName(nickName)
                .profileImg(profileImg)
                .build();

        userRepository.save(user);
        log.info("새로운 회원 저장 완료");
        return user.getId();
    }
}
