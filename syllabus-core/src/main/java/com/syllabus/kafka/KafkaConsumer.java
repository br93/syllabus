package com.syllabus.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.syllabus.data.StudentResponse;

@Service
public class KafkaConsumer {
    
    private StudentResponse message;

    @KafkaListener(topics = KafkaConstants.TOPIC_NAME, groupId = KafkaConstants.GROUP_ID)
    public void consume(StudentResponse message){
        this.message = message;
        System.out.println("Message received");
    }

    public StudentResponse getMessage(){
        return this.message;
    }
}
