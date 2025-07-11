package com.padwan.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.padwan.test")
@SpringBootApplication
public class JediApplication {

	public static void main(String[] args) {
		SpringApplication.run(JediApplication.class, args);
	}

}
