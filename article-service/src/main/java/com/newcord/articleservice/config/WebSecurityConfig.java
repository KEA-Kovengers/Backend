package com.newcord.articleservice.config;

import com.newcord.articleservice.global.jwt.JwtFilter;
import com.newcord.articleservice.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final CorsConfig corsConfig;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) //csrf 비활성화
            .cors(AbstractHttpConfigurer::disable)
            // 세션 사용 안함
            .sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // 인증 필요한 경로 설정
            .addFilter(corsConfig.corsFilter())//CorsFilter 등록
             //Http 요청에 대한 Jwt 유효성 선 검사, 이 부분은 유저서비스에 REST API 요청으로 구현할 예정
            .addFilterBefore(new JwtFilter(jwtTokenProvider, restTemplate()), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}