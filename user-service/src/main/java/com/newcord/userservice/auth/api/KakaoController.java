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
    private final KakaoAuthService kakaoAuthService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    @ResponseBody
    public ApiResponse<HashMap<String, String>> kakaoOauth(@RequestParam("code") String code) {
        log.info("인가 코드를 이용하여 토큰을 받습니다.");
        KakaoTokenResponse kakaoTokenResponse = kakaoTokenJsonData.getToken(code);
        log.info("토큰에 대한 정보입니다.{}",kakaoTokenResponse);

        ApiResponse<HashMap<String, String>> response = kakaoAuthService.authCheck(kakaoTokenResponse.getAccess_token());
        log.info("jwt 토큰 생성 완료: {}", response.getResult().get("token"));
        log.info("jwt refresh 토큰 생성 완료: {}", response.getResult().get("refreshToken"));

        return response;
    }

}
