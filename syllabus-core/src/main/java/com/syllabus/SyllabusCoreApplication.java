package com.syllabus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SyllabusCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyllabusCoreApplication.class, args);
	}

}
