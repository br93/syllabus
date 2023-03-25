package com.syllabus.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="auth-ms")
public interface AuthClient {

    @GetMapping("/api/v1/auth/user")
    public ClientResponse getMe(@RequestHeader("Cookie") String cookie);
}
