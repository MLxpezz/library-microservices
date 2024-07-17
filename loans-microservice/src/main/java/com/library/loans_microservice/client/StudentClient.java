package com.library.loans_microservice.client;

import com.library.loans_microservice.http.request.StudentClientRequest;
import com.library.loans_microservice.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class StudentClient {

    @Bean
    public StudentClientRequest studentClientRequest() {
        RestClient restClient = RestClient.builder().baseUrl("http://localhost:8080/api/students/")
                .requestInterceptor(new JwtInterceptor())
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter)
                .build();

        return factory.createClient(StudentClientRequest.class);
    }
}
