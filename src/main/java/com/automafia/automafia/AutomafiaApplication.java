package com.automafia.automafia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AutomafiaApplication {

	private static final Logger log = LoggerFactory.getLogger(AutomafiaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AutomafiaApplication.class, args);
	}

}
