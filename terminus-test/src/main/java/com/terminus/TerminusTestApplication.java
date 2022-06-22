package com.terminus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TerminusTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TerminusTestApplication.class, args);
	}

}
