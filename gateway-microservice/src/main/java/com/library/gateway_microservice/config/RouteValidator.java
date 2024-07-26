package com.library.gateway_microservice.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private static final List<String> publicEndpoints = List.of("/api/users/register", "/api/users/login", "/api/users/validate-token");

    public Predicate<ServerHttpRequest> isSecured = request -> publicEndpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
