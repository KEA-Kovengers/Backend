//package com.newcord.userservice.auth.jwt;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.GenericFilterBean;
//
//import java.io.IOException;
//
//@RequiredArgsConstructor
//public class JwtAuthFilter extends GenericFilterBean {
//
//    private final JwtTokenProvider jwtTokenProvider;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        // 클라이언트의 API 요청 헤더에서 토큰 추출
//        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
//
//        // 유효성 검사 후 SecurityContext에 저장
//        if (token != null) {
//            if (jwtTokenProvider.validateToken(token)) {
//                Authentication authentication = jwtTokenProvider.getAuthentication(token);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } else if (jwtTokenProvider.validateRefreshToken(token)) {
//                // 만료된 액세스 토큰에 대한 리프레시 토큰 검증
//                String newToken = jwtTokenProvider.refreshAccessToken(token);
//                if (newToken != null) {
//                    // 새로운 액세스 토큰이 발급되면 SecurityContext에 저장
//                    Authentication authentication = jwtTokenProvider.getAuthentication(newToken);
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    // 응답 헤더에 새로운 액세스 토큰을 설정하거나, 클라이언트에게 전달할 수 있음
//                    // 이 예제에서는 응답 헤더에는 변경하지 않음
//                }
//            }
//        }
//
//        // 다음 필터링
//        chain.doFilter(request, response);
//    }
//}
