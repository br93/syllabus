package com.syllabus.client;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClientInterceptor implements RequestInterceptor {

    private final HttpServletRequest request;
    private static final String CONTENT_TYPE = "application/json";

    @Override
    public void apply(RequestTemplate template) {
        template.header("Content-Type", CONTENT_TYPE);
        template.header("Accept", CONTENT_TYPE);
        template.header("Cookie", request.getHeader("Cookie"));
    }

}
