package com.syllabus.util;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ConstantUtil {

    private final Environment environment;

    public String getRequiredType() {
        return environment.getProperty("required.course");
    }

    public String getSecondLayer() {
        return environment.getProperty("second.layer.course");
    }

    public String getServiceUnavailableMessage(){
        return environment.getProperty("message.service.unavailable");
    }
}
