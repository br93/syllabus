package com.syllabus.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class AuthClient {

    private final WebClient webClient;

    public AuthClient(@Value("${api.gateway}") final String apiGateway, @Value("${auth.path}") final String authPath) {

        this.webClient = WebClient.builder().baseUrl(apiGateway.concat(authPath)).build();
    }

    @GetMapping("user")
    public Mono<ClientResponse> getMe(String cookie) {

        return this.webClient.get().uri("user").header("Cookie", cookie).retrieve().bodyToMono(ClientResponse.class);
    }
}
