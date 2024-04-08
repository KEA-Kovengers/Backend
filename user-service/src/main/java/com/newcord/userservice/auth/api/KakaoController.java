package com.newcord.userservice.auth.api;


import com.newcord.userservice.auth.jwt.JwtTokenProvider;
import com.newcord.userservice.auth.response.ApiResponse;
import com.newcord.userservice.auth.response.ResponseCode;
import com.newcord.userservice.auth.service.KakaoAuthService;
import com.newcord.userservice.auth.utils.KakaoTokenJsonData;
import com.newcord.userservice.auth.utils.dto.KakaoTokenResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class KakaoController {
    private final KakaoTokenJsonData kakaoTokenJsonData;
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoAuthService kakaoAuthService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @Description("회원이 소셜 로그인을 마치면 자동으로 실행되는 API입니다. 인가 코드를 이용해 토큰을 받고, 해당 토큰으로 사용자 정보를 조회합니다." + "사용자 정보를 이용하여 서비스에 회원가입합니다.")
    @GetMapping("/login")
    @ResponseBody
    public ApiResponse<HashMap<String, String>> kakaoOauth(@RequestParam("code") String code) {
        log.info("인가 코드를 이용하여 토큰을 받습니다.");
        KakaoTokenResponse kakaoTokenResponse = kakaoTokenJsonData.getToken(code);
        log.info("토큰에 대한 정보입니다.{}",kakaoTokenResponse);

        ApiResponse<HashMap<String, String>> response = authCheck(kakaoTokenResponse.getAccess_token());
        log.info("jwt 토큰 생성 완료: {}", response.getData().get("token"));

        return response;
    }

    // 카카오 로그인을 위해 회원가입 여부 확인, Jwt 토큰 발급
    public ApiResponse<HashMap<String, String>> authCheck(@RequestHeader String accessToken) {
        Long userId = kakaoAuthService.isSignedUp(accessToken); // 유저 고유번호 추출
        String refreshToken = jwtTokenProvider.createRefreshToken(userId.toString());
        kakaoAuthService.saveRefreshToken(userId, refreshToken); // 리프레시 토큰 db 저장

        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId.toString());
        map.put("token", jwtTokenProvider.createToken(userId.toString()));
        map.put("refreshToken", refreshToken);
        return ApiResponse.success(map, ResponseCode.USER_LOGIN_SUCCESS.getMessage());
    }


}
