package com.library.loans_microservice.client;

import com.library.loans_microservice.http.request.AuthClientRequest;
import com.library.loans_microservice.http.request.StudentClientRequest;
import com.library.loans_microservice.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class AuthClient {

    @Value("${api-key}")
    private String apiKey;

    @Bean
    public AuthClientRequest authClientRequest() {
        RestClient restClient = RestClient.builder().baseUrl("http://localhost:8093/api/users/")
                .defaultHeader("api-x-key", apiKey)
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter)
                .build();

        return factory.createClient(AuthClientRequest.class);
    }
}
