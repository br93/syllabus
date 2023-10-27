package com.syllabus.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class MappingUtil {

    public String getDate(Instant instant) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());

        return formatter.format(instant);
    }
    
}
