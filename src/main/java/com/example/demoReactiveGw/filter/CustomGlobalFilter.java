package com.example.demoReactiveGw.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered{
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("GG custom global filter ");
        ServerHttpRequest request = exchange.getRequest();
        log.info("GG custom global filter {}",request.getURI().getPath());
        if( "/hsvc/test3".equals( request.getURI().getPath() )) {
            log.info("GG custom global filter"+ "/hsvc/test3");
            ServerHttpRequest newRequest = exchange.getRequest().mutate().path("/hsvc/test").build();

            return chain.filter(exchange.mutate().request(newRequest).build());
        }

        String body = exchange.getAttribute(ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR);

        var t = request.getHeaders().getFirst("test2");
        log.info("custom Filter baseMessage : t: {} ,body : {}",t , body);




        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }

}
