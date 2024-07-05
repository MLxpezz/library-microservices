package com.library.loans_microservice.client;

import com.library.loans_microservice.http.request.BookClientRequest;
import com.library.loans_microservice.http.request.StudentClientRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class BookClient {

    @Bean
    public BookClientRequest bookClientRequest() {
        RestClient restClient = RestClient.builder().baseUrl("http://localhost:8080/api/books/").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(BookClientRequest.class);
    }
}
