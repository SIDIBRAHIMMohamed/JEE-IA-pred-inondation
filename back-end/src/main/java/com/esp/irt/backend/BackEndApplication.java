package com.esp.irt.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication(exclude = BatchAutoConfiguration.class)
public class BackEndApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(BackEndApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BackEndApplication.class, args);

		LOGGER.info("This is information");
		LOGGER.debug("This is debug");
		LOGGER.error("This is error");
	}

}
