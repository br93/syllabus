package com.syllabus.kafka.producer;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;

import com.syllabus.data.StudentResponse;
import com.syllabus.kafka.topic.TopicConstants;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MessageProducerService {

    private final KafkaTemplate<String, StudentResponse> kafkaTemplate;
    private final MessageTrackerService messageTrackerService;

    public void produceMessageIfNotProduced(String id, StudentResponse data) {
        if (!messageTrackerService.isMessageProduced(id)) {
            Message<StudentResponse> message = this.messageBuilder(data);
            kafkaTemplate.send(message);
            
            System.out.println("Message sent -> id = " + id);
            messageTrackerService.markMessageAsProduced(data);
        }
    }

    private Message<StudentResponse> messageBuilder(StudentResponse data){
        return MessageBuilder.withPayload(data).setHeader(KafkaHeaders.TOPIC, TopicConstants.TOPIC_NAME).build();
    }


    
}
