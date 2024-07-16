package com.library.gateway_microservice.config.filter;

import com.library.gateway_microservice.config.RouteValidator;
import com.library.gateway_microservice.config.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationFilter implements GatewayFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RouteValidator routeValidator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if(routeValidator.isSecured.test(request)) {
            if(!request.getHeaders().containsKey("Authorization")) {
                System.out.println("NO AUTORIZADO PERROOOOO");
                return this.onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            String token = request.getHeaders().getFirst("Authorization").substring(7);

            if(jwtUtils.validateToken(token)) {
                System.out.println("SI PASASTEEEEEE");
                Claims claims = jwtUtils.getClaimsFromToken(token);
                exchange.getRequest().mutate().header("email", String.valueOf(claims.get("email"))).build();
            }
        }

        System.out.println("NO AUTORIZADO PAPA");
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
}
