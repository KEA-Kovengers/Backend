package com.newcord.userservice.domain.auth.service;

import com.newcord.userservice.domain.auth.jwt.JwtTokenProvider;
import com.newcord.userservice.global.common.exception.ApiException;
import com.newcord.userservice.global.common.response.ApiResponse;
import com.newcord.userservice.domain.auth.utils.KakaoUserInfo;
import com.newcord.userservice.domain.auth.utils.dto.KakaoUserInfoResponse;
import com.newcord.userservice.global.common.response.code.status.ErrorStatus;
import com.newcord.userservice.domain.user.domain.Users;
import com.newcord.userservice.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoAuthService {

    private final KakaoUserInfo kakaoUserInfo;
    private final UsersRepository usersRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final WebClient webClient;

    // 카카오에서 엑세스 토큰 받아와서 회원정보 가져오고 검증함
    @Transactional
    public Long getUser(String token) {
        KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(token);
        log.info("회원 정보 입니다.{}", userInfo);
        return userInfo.getId();
    }

    @Transactional
    public boolean isUserExists(Long userId) {
        return usersRepository.findById(userId).isPresent();
    }

    // 유저 정보 추가
    @Transactional
    public Long createUser(String token) {
        KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(token);
        log.info("회원 정보 입니다.{}", userInfo);

        Users user = Users.builder()
                .id(userInfo.getId())
                .nickName(userInfo.getProperties().getNickname())
                .blogName(userInfo.getProperties().getNickname()+"의 블로그")
                .profileImg(userInfo.getProperties().getProfile_image())
                .role("USER")
                .build();

        usersRepository.save(user);
        log.info("새로운 회원 저장 완료");
        return userInfo.getId();
    }

    // 리프레시 토큰 DB 저장
    @Transactional
    public void saveRefreshToken(Long userId, String refreshToken) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
        user.setRefreshToken(refreshToken);
        usersRepository.save(user);
    }

    // 카카오 엑세스 토큰 DB 저장
    @Transactional
    public void saveKakaoToken(Long userId, String kakaoToken) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
        user.setKakaoToken(kakaoToken);
        usersRepository.save(user);
    }

    // 카카오 로그인: 회원가입 여부 확인, JWT 엑세스/리프레시 토큰 발급
    @Transactional
    public ApiResponse<HashMap<String, String>> authCheck(String accessToken) {
        Long userId = getUser(accessToken); // 토큰으로 정보 가져옴
        boolean userExists = isUserExists(userId); // 유저 존재 하는지 확인

        if (!userExists) {
            createUser(accessToken);

        }

        saveKakaoToken(userId, accessToken);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId.toString());
        saveRefreshToken(userId, refreshToken);

        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId.toString());
        map.put("token", jwtTokenProvider.createToken(userId.toString()));
        map.put("refreshToken", refreshToken);
        map.put("status", userExists ? "old" : "new");

        return ApiResponse.onSuccess(map);
    }

    @Transactional
    public void deleteUserAndUnlinkKakao(String token) {
        Long userId = getUserIdFromToken(token);
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
        String kakaoToken = user.getKakaoToken();

        unlinkKakaoUser(kakaoToken);
        usersRepository.deleteById(userId);
    }

    @Transactional
    public void deleteUserByRefreshToken(String refreshToken) {
        Long userId = getUserIdFromToken(refreshToken);
        usersRepository.deleteById(userId);
    }

    // JWT 토큰에서 사용자 ID 추출
    private Long getUserIdFromToken(String token) {
        String userIdString = jwtTokenProvider.getUserPk(token);
        return Long.parseLong(userIdString);
    }

    // 카카오 연결 끊기
    private void unlinkKakaoUser(String accessToken) {
        WebClient.ResponseSpec responseSpec = webClient.post()
                .uri("https://kapi.kakao.com/v1/user/unlink")
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve();

        String responseBody = responseSpec.bodyToMono(String.class).block();
        log.info("카카오 사용자 연결 해제 응답: {}", responseBody);
    }
}
