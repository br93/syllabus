package com.syllabus.util;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MessageUtil {

    private final Environment environment;

    public String getServiceUnavailableMessage(){
        return environment.getProperty("message.service.unavailable");
    }
    
}
