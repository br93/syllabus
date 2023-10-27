package com.syllabus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SyllabusDiscoveryService {

	public static void main(String[] args) {
		SpringApplication.run(SyllabusDiscoveryService.class, args);
	}

}
