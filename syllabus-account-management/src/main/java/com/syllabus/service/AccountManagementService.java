package com.syllabus.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.syllabus.client.AuthClient;
import com.syllabus.client.ClientResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountManagementService {

    private final AuthClient authClient;

    public String extractCookie(Map<String, String> headers) {
        var values = headers.values();

        return values.stream().filter(x -> x.contains("Authorization")).findFirst().orElseThrow(RuntimeException::new);
    }

    public ClientResponse getUser(String authorization) {
        return authClient.getMe(authorization);
    }

}
