package com.project.test.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringJenkinsApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void add() {
		assertEquals(5, 2+3);
	}
}
