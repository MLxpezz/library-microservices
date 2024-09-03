package com.library.loans_microservice.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

@Component
public class JwtInterceptor implements ClientHttpRequestInterceptor {


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String token = null;
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest currentRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token = currentRequest.getHeader(HttpHeaders.AUTHORIZATION);
        }
        if (token != null && token.startsWith("Bearer ")) {
            request.getHeaders().add(HttpHeaders.AUTHORIZATION, token);
        }
        return execution.execute(request, body);
    }

}
