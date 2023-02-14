package com.gwpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class Gpoc0Application {

	public static void main(String[] args) {
		SpringApplication.run(Gpoc0Application.class, args);
	}

}
