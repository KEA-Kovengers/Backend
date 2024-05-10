package com.newcord.userservice.auth.service;

import com.newcord.userservice.auth.jwt.JwtTokenProvider;
import com.newcord.userservice.auth.response.ApiResponse;
import com.newcord.userservice.auth.response.ResponseCode;
import com.newcord.userservice.auth.exception.UserException;
import com.newcord.userservice.auth.utils.KakaoUserInfo;
import com.newcord.userservice.auth.utils.dto.KakaoUserInfoResponse;
import com.newcord.userservice.user.domain.Users;
import com.newcord.userservice.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoAuthService {

    private final KakaoUserInfo kakaoUserInfo;
    private final UsersRepository usersRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Long isSignedUp(String token) {
        KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(token);
        log.info("회원 정보 입니다.{}", userInfo);

        Long userId = userInfo.getId();
        Users user = usersRepository.findById(userId).orElse(null);
        if (user == null) {
            // 사용자가 존재하지 않는 경우에만 createUser 호출
            return createUser(userId, userInfo.getProperties().getNickname(), userInfo.getProperties().getProfile_image());
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

    // refresh token db 저장
    @Transactional
    public void saveRefreshToken(Long id, String refreshToken) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));
        user.setRefreshToken(refreshToken);
        usersRepository.save(user);
    }

    // 카카오 로그인을 위해 회원가입 여부 확인, Jwt 엑세스/리프레시 토큰 발급
    @Transactional
    public ApiResponse<HashMap<String, String>> authCheck(@RequestHeader String accessToken) {
        Long userId = isSignedUp(accessToken); // 유저 고유번호 추출
        String refreshToken = jwtTokenProvider.createRefreshToken(userId.toString());
        saveRefreshToken(userId, refreshToken); // 리프레시 토큰 db 저장

        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId.toString());
        map.put("token", jwtTokenProvider.createToken(userId.toString()));
        map.put("refreshToken", refreshToken);
        return ApiResponse.success(map, ResponseCode.USER_LOGIN_SUCCESS.getMessage());
    }

}
