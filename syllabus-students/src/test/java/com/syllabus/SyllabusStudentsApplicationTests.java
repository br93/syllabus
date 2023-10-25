package com.syllabus;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ContextConfiguration(classes = MongoContainerConfig.class)
@ActiveProfiles("test")
class SyllabusStudentsApplicationTests {

	@Test
	void contextLoads() {
	}

}
