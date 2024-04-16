package com.newcord.gatewayservice.component;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class G1Filter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("pre global filter order -1"); // pre filter MSA 들어가기 이전에 되는 거

        return chain.filter(exchange) // post filter / MSA 나오면서 client 에게 가기 전에 되는 거
                .then(Mono.fromRunnable(() -> {

                    System.out.println("post global filter order -1");
                }));
    }

    @Override
    public int getOrder() { // 순번 정하기 / 숫자가 작을 수록 client 와 가까이

        return -1;
    }

}