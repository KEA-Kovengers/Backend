package com.newcord.articleservice.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.ApiResponse;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Enumeration;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final RestTemplate restTemplate;
    private String[] balckListArray = {
        "/report/**",
        "/posts/createPost", "/posts/editPost", "/posts/updateHashtags", "/posts/deleteEditSession", "/posts/createEditSession"
        ,"/comment/delete", "/articles/comment/create"};
    private List<String> blackList = Arrays.asList(balckListArray);


    @Override
    public void doFilter(
        ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, ApiException {

        String rUrl = ((HttpServletRequest) request).getRequestURI();
        if(!blackList.contains(rUrl)){
            chain.doFilter(request, response);
            return;
        }
        String token = ((HttpServletRequest) request).getHeader("Authorization");

        if(token == null || !token.startsWith("Bearer ")){
            response.setContentType("application/json");
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FORBIDDEN);

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(ErrorStatus._UNAUTHORIZED.getReasonHttpStatus());
            response.getWriter().write(json);
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try{
            ResponseEntity<Map> authResponse = restTemplate.exchange("http://newcord.kro.kr/users/auth/validate", HttpMethod.GET,
                entity, Map.class);

            if(Boolean.FALSE.equals(authResponse.getBody().get("result"))){
                response.setContentType("application/json");
                ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FORBIDDEN);

                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(ErrorStatus._UNAUTHORIZED.getReasonHttpStatus());
                response.getWriter().write(json);
                return;
            }
        } catch (Exception e){
            response.setContentType("application/json");
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FORBIDDEN);

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(ErrorStatus._UNAUTHORIZED.getReasonHttpStatus());
            response.getWriter().write(json);
            return;
        }

        // 다음 필터링
        chain.doFilter(request, response);
    }

    private void handleException(HttpServletResponse response, ApiException ex) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    }

}
