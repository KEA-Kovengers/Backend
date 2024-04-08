package com.newcord.userservice.auth.service;

import com.newcord.userservice.auth.response.ResponseCode;
import com.newcord.userservice.auth.exception.UserException;
import com.newcord.userservice.auth.utils.KakaoUserInfo;
import com.newcord.userservice.auth.utils.dto.KakaoUserInfoResponse;
import com.newcord.userservice.user.domain.Users;
import com.newcord.userservice.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoAuthService {

    private final KakaoUserInfo kakaoUserInfo;
    private final UsersRepository usersRepository;

    @Transactional
    public Long isSignedUp(String token) {
        KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(token);
        log.info("회원 정보 입니다.{}", userInfo);

        Long userId = userInfo.getId();
        Users user = usersRepository.findById(userId).orElse(null);
        if (user == null) {
            // 사용자가 존재하지 않는 경우에만 createUser 호출
            return createUser(userInfo.getId(), userInfo.getProperties().getNickname(), userInfo.getProperties().getProfile_image());
        }
        return userId;
    }

    @Transactional
    public Long createUser(Long id, String nickName, String profileImg) {
        Users user = Users.builder()
                .id(id)
                .nickName(nickName)
                .profileImg(profileImg)
                .build();

        usersRepository.save(user);
        log.info("새로운 회원 저장 완료");
        return user.getId();
    }
}
