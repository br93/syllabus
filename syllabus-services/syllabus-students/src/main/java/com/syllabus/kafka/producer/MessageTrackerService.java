package com.syllabus.kafka.producer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.syllabus.data.StudentResponse;

@Service
public class MessageTrackerService {

    private Map<String, StudentResponse> producedMessages = new HashMap<>();

    public boolean isMessageProduced(String id, int count) {
        return producedMessages.containsKey(id + count);
    }

    public void markMessageAsProduced(StudentResponse message) {
        String id = message.getStudentId() + (message.getCourseCodes().size());
        producedMessages.put(id, message);
    }
    
}
