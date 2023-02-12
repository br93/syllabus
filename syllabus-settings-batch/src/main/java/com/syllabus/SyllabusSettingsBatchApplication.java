package com.syllabus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SyllabusSettingsBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyllabusSettingsBatchApplication.class, args);
	}

}
