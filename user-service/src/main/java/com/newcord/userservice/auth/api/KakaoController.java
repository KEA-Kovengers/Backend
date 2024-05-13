package com.newcord.userservice.auth.api;

import com.newcord.userservice.auth.jwt.JwtTokenProvider;
import com.newcord.userservice.global.common.response.ApiResponse;
import com.newcord.userservice.auth.service.KakaoAuthService;
import com.newcord.userservice.auth.utils.KakaoTokenJsonData;
import com.newcord.userservice.auth.utils.dto.KakaoTokenResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping("/users/auth")
@RequiredArgsConstructor
@Slf4j
public class KakaoController {
    private final KakaoTokenJsonData kakaoTokenJsonData;
    private final KakaoAuthService kakaoAuthService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 카카오 로그인 + 토큰 발급
    @GetMapping("/login")
    @ResponseBody
    public ApiResponse<HashMap<String, String>> kakaoOauth(@RequestParam("code") String code) {
        log.info("인가 코드를 이용하여 토큰을 받습니다.");
        KakaoTokenResponse kakaoTokenResponse = kakaoTokenJsonData.getToken(code);
        log.info("토큰에 대한 정보입니다.{}", kakaoTokenResponse);

        // 카카오 토큰을 사용하여 회원가입 여부를 확인하고 Jwt 토큰을 발급
        ApiResponse<HashMap<String, String>> response = kakaoAuthService.authCheck(kakaoTokenResponse.getAccess_token());

        return response;
    }

    // jwt 엑세스 토큰을 받으면 토큰을 해독하여 유저 아이디를 조회하고 반환
    @GetMapping("/decode")
    @ResponseBody
    public ApiResponse<Long> decodeToken(@RequestHeader("X-AUTH-TOKEN") String jwtToken) {
        String userIdString = jwtTokenProvider.getUserPk(jwtToken);
        log.info("해독된 유저 아이디: {}", userIdString);

        // 유저 아이디를 Long 타입으로 변환
        Long userId = Long.parseLong(userIdString);

        // 유저 아이디를 가지고 디비에 있는지 확인
        boolean userExists = kakaoAuthService.isUserExists(userId);
        if (!userExists) {
            log.error("User not found in database with ID: {}", userId);
            return ApiResponse.onFailure("USER_NOT_FOUND", "User not found in database", null);
        }
        return ApiResponse.onSuccess(userId);
    }

    // jwt 엑세스 토큰 유효성을 검증
    @GetMapping("/validate")
    @ResponseBody
    public ApiResponse<Boolean> validateToken(@RequestHeader("X-AUTH-TOKEN") String jwtToken) {
        boolean isValid = jwtTokenProvider.validateToken(jwtToken);
        log.info("JWT 토큰 유효성 검증 결과: {}", isValid);
        return ApiResponse.onSuccess(isValid);
    }

    // jwt 리프레시 토큰 유효할 때 jwt 엑세스 토큰 재발급
    @GetMapping("/issueToken")
    @ResponseBody
    public ApiResponse<HashMap<String, String>> issueToken(@RequestHeader("X-REFRESH-TOKEN") String refreshToken) {
        boolean isRefreshTokenValid = jwtTokenProvider.validateRefreshToken(refreshToken);
        if (isRefreshTokenValid) {
            String accessToken = jwtTokenProvider.refreshAccessToken(refreshToken);
            HashMap<String, String> map = new HashMap<>();
            map.put("accessToken", accessToken);
            log.info("발급된 액세스 토큰: {}", accessToken);
            return ApiResponse.onSuccess(map);
        } else {
            log.error("JWT 리프레시 토큰이 유효하지 않습니다.");
            return ApiResponse.onFailure("REFRESH_TOKEN_INVALID", "Refresh token validation failed.", null);
        }
    }

}