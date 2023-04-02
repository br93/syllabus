package com.syllabus.client;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class AuthClient {

    private final WebClient webClient;
    private static final String BASE_URL = "http://localhost:8765/api/v1/auth/";

    public AuthClient() {
        this.webClient = WebClient.builder().baseUrl(BASE_URL).build();
    }

    @GetMapping("user")
    public Mono<ClientResponse> getMe(String cookie) {

        return this.webClient.get().uri("user").header("Cookie", cookie).retrieve().bodyToMono(ClientResponse.class);
    }
}