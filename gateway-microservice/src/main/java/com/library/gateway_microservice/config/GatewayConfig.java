package com.library.gateway_microservice.config;

import com.library.gateway_microservice.config.filter.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private AuthorizationFilter authorizationFilter;

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
                .build();
    }
}
