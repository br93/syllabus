package com.syllabus.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SettingsClient {

    private final WebClient webClient;
    private static final String BASE_URL = "${api.gateway}" + "${settings.path}";

    public SettingsClient() {
        this.webClient = WebClient.builder().baseUrl(BASE_URL).build();
    }

    /*@GetMapping("user")
    public Mono<ClientResponse> getMe(String cookie) {

        return this.webClient.get().uri("user").header("Cookie", cookie).retrieve().bodyToMono(ClientResponse.class);
    }*/
}
