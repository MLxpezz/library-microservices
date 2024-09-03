package com.library.loans_microservice.http.request;

import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface AuthClientRequest {

    @PostExchange("/microservice-token")
    String getToken();
}
