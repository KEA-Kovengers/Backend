package com.newcord.userservice.config;

import com.newcord.userservice.domain.auth.jwt.JwtAuthFilter;
import com.newcord.userservice.domain.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) //csrf 비활성화
                // 세션 사용 안함
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 회원가입, 로그인 관련 API는 Jwt 인증 없이 접근 가능
                .authorizeHttpRequests((requests) ->
                        requests.requestMatchers("/users/auth/**","/","/index.html","/v3/api-docs/**","/swagger-ui/index.html","/swagger-ui/**", "/swagger-resources/**").permitAll()
                        // 나머지 모든 API는 Jwt 인증 필요
                        .anyRequest().authenticated())
                .addFilter(corsConfig.corsFilter()) //CorsFilter 등록
                // Http 요청에 대한 Jwt 유효성 선 검사
                .addFilterBefore(new JwtAuthFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}