package com.syllabus.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {

     @Bean
    public NewTopic studentsTopic(){
        return TopicBuilder.name("students-topic")
                .build();
    }
    
}
