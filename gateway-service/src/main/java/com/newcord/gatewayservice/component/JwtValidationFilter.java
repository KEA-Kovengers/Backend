package com.newcord.gatewayservice.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtValidationFilter implements GlobalFilter, Ordered {

    private final JwtValidator jwtValidator;

    @Autowired
    public JwtValidationFilter(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("111222333");
        ServerHttpRequest request = exchange.getRequest();
        System.out.println("111222333");
        System.out.println(request);
        String token = request.getHeaders().getFirst("Token"); // 헤더에서 JWT 토큰 가져오기

        try {
            System.out.println(jwtValidator.createToken(token));
            System.out.println("111222333token");
            System.out.println(token);
            if (jwtValidator.validateToken(token)) {
                // 유효한 토큰이면 필터 체인 진행
                System.out.println("111222333token");
                return chain.filter(exchange);
            } else {
                // 유효하지 않은 토큰이면 401 Unauthorized 응답 반환
                System.out.println("111222token");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        } catch (Error e) {
            System.out.print(e.toString());
            return chain.filter((exchange));
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
