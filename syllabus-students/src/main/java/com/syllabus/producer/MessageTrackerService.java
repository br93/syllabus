package com.syllabus.producer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.syllabus.data.StudentResponse;

@Service
public class MessageTrackerService {

     private final Map<String, StudentResponse> producedMessages = new HashMap<>();

    public boolean isMessageProduced(String id) {
        return producedMessages.containsKey(id);
    }

    public void markMessageAsProduced(StudentResponse message) {
        producedMessages.put(message.getStudentId(), message);
    }
    
}
