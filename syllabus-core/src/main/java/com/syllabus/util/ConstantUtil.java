package com.syllabus.util;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ConstantUtil {

    private final Environment environment;

    public String constantRequiredType() {
        return environment.getProperty("required.course");
    }

    public String constantSecondLayer() {
        return environment.getProperty("second.layer.course");
    }

    public String serviceUnavailableMessage(){
        return environment.getProperty("message.service.unavailable");
    }
}
