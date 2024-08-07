package com.library.gateway_microservice.config.filter;

import com.library.gateway_microservice.config.RouteValidator;
import com.library.gateway_microservice.config.utils.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class AuthorizationFilter implements GatewayFilter {

    private final JwtUtils jwtUtils;

    private final RouteValidator routeValidator;

    public AuthorizationFilter(JwtUtils jwtUtils, RouteValidator routeValidator) {
        this.jwtUtils = jwtUtils;
        this.routeValidator = routeValidator;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if(routeValidator.isSecured.test(request)) {
            if(!request.getHeaders().containsKey("Authorization")) {
                return this.onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            String token = Objects.requireNonNull(request.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0).substring(7);

            if(jwtUtils.validateToken(token)) {
                exchange.getRequest().mutate()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .build();
            } else {
                return this.onError(exchange, HttpStatus.UNAUTHORIZED);
            }
        }

        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
}
