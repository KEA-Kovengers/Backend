package com.newcord.userservice.domain.auth.api;

import com.newcord.userservice.domain.auth.jwt.JwtTokenProvider;
import com.newcord.userservice.domain.auth.service.KakaoAuthService;
import com.newcord.userservice.domain.auth.utils.KakaoTokenJsonData;
import com.newcord.userservice.global.common.exception.ApiException;
import com.newcord.userservice.global.common.response.ApiResponse;
import com.newcord.userservice.domain.auth.utils.dto.KakaoTokenResponse;

import com.newcord.userservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping("/users/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Auth", description = "로그인 API")
public class KakaoController {
    private final KakaoTokenJsonData kakaoTokenJsonData;
    private final KakaoAuthService kakaoAuthService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    @Operation(summary = "로그인", description = "카카오 엑세스 토큰으로 사용자 정보를 가져옵니다.")
    @ResponseBody
    public ApiResponse<HashMap<String, String>> kakaoOauth(@RequestParam("code") String code) {
        log.info("인가 코드를 이용하여 토큰을 받습니다.");
        KakaoTokenResponse kakaoTokenResponse = kakaoTokenJsonData.getToken(code);
        log.info("토큰에 대한 정보입니다.{}", kakaoTokenResponse);
        // 카카오 토큰을 사용하여 회원가입 여부를 확인하고 Jwt 토큰을 발급
        ApiResponse<HashMap<String, String>> response = kakaoAuthService.authCheck(kakaoTokenResponse.getAccess_token());

        return response;
    }

    @GetMapping("/test/token")
    public String getTestToken() {
        String userId = "123456789";
        String token = jwtTokenProvider.createToken(userId);
        return token;
    }

    // jwt 엑세스 토큰을 받으면 토큰을 해독하여 유저 아이디를 조회하고 반환
    @GetMapping("/decode")
    @Operation(summary = "토큰 해독 및 DB 조회", description = "엑세스 토큰을 해독하여 유저아이디로 DB 유효성을 조회합니다.")
    @ResponseBody
    public ApiResponse<Long> decodeToken(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 부분만 추출
            String userIdString = jwtTokenProvider.getUserPk(jwtToken);
            log.info("해독된 유저 아이디: {}", userIdString);

            Long userId = Long.parseLong(userIdString);

            // 유저 아이디를 가지고 디비에 있는지 확인
            boolean userExists = kakaoAuthService.isUserExists(userId);
            if (!userExists) {
                throw new ApiException(ErrorStatus._USER_NOT_FOUND);
            }
            return ApiResponse.onSuccess(userId);
        } else {
            throw new ApiException(ErrorStatus._INVALID_AUTH_HEADER);
        }
    }

    // jwt 엑세스 토큰 유효성을 검증
    @GetMapping("/validate")
    @Operation(summary = "토큰 유효성 검증", description = "엑세스 토큰의 유효성을 검증합니다.")
    @ResponseBody
    public ApiResponse<Boolean> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 부분만 추출
            boolean isValid = jwtTokenProvider.validateToken(jwtToken);
            log.info("JWT 토큰 유효성 검증 결과: {}", isValid);
            if(!isValid){
                throw new ApiException(ErrorStatus._ACCESS_TOKEN_INVALID);
            }else{
                return ApiResponse.onSuccess(isValid);
            }
        } else {
            throw new ApiException(ErrorStatus._INVALID_AUTH_HEADER);
        }
    }

    // jwt 리프레시 토큰 유효할 때 jwt 엑세스 토큰 재발급
    @GetMapping("/issueToken")
    @Operation(summary = "토큰 재발급", description = "리프레시 토큰이 유효할 때 엑세스 토큰을 재발급합니다.")
    @ResponseBody
    public ApiResponse<HashMap<String, String>> issueToken(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String refreshToken = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 부분만 추출
            boolean isRefreshTokenValid = jwtTokenProvider.validateRefreshToken(refreshToken);
            if (isRefreshTokenValid) {
                String accessToken = jwtTokenProvider.refreshAccessToken(refreshToken);
                HashMap<String, String> map = new HashMap<>();
                map.put("accessToken", accessToken);
                log.info("발급된 액세스 토큰: {}", accessToken);
                return ApiResponse.onSuccess(map);
            } else {
                throw new ApiException(ErrorStatus._REFRESH_TOKEN_INVALID);
            }
        } else {
            throw new ApiException(ErrorStatus._INVALID_AUTH_HEADER);
        }
    }

//    @PostMapping("/logout")
//    @Operation(summary = "로그아웃", description = "리프레시 토큰을 받아 사용자 정보를 삭제합니다.")
//    @ResponseBody
//    public ApiResponse<Void> logout(@RequestHeader("Authorization") String authorizationHeader) {
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            String refreshToken = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 부분만 추출
//            boolean isRefreshTokenValid = jwtTokenProvider.validateRefreshToken(refreshToken);
//            if (isRefreshTokenValid) {
//                kakaoAuthService.deleteUserByRefreshToken(refreshToken);
//                return ApiResponse.onSuccess(null);
//            } else {
//                throw new ApiException(ErrorStatus._REFRESH_TOKEN_INVALID);
//            }
//        } else {
//            throw new ApiException(ErrorStatus._INVALID_AUTH_HEADER);
//        }
//    }

    @PostMapping("/withdraw")
    @Operation(summary = "회원 탈퇴", description = "엑세스 토큰을 받아 사용자 정보를 삭제하고 카카오 연결을 끊습니다.")
    @ResponseBody
    public ApiResponse<Void> withdraw(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 부분만 추출
            boolean isValid = jwtTokenProvider.validateToken(jwtToken);
            if (isValid) {
                kakaoAuthService.deleteUserAndUnlinkKakao(jwtToken);
                return ApiResponse.onSuccess(null);
            } else {
                throw new ApiException(ErrorStatus._ACCESS_TOKEN_INVALID);
            }
        } else {
            throw new ApiException(ErrorStatus._INVALID_AUTH_HEADER);
        }
    }
}