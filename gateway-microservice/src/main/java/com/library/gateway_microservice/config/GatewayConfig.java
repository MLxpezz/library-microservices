package com.library.gateway_microservice.config;

import com.library.gateway_microservice.config.filter.AuthorizationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final AuthorizationFilter authorizationFilter;

    public GatewayConfig(AuthorizationFilter authorizationFilter) {
        this.authorizationFilter = authorizationFilter;
    }

    @Bean
    RouteLocator routes(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("msvc-auth", route -> route.path("/api/users/**")
                        .filters(filter -> filter.filter(authorizationFilter))
                        .uri("lb://msvc-auth"))
                .route("msvc-students", route -> route.path("/api/students/**")
                        .filters(filter -> filter.filter(authorizationFilter))
                        .uri("lb://msvc-students"))
                .route("msvc-books", route -> route.path("/api/books/**")
                        .filters(filter -> filter.filter(authorizationFilter))
                        .uri("lb://msvc-books"))
                .route("msvc-loans", route -> route.path("/api/loans/**")
                        .filters(filter -> filter.filter(authorizationFilter))
                        .uri("lb://msvc-loans"))
                .build();
    }
}
