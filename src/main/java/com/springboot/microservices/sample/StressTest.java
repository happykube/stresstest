package com.springboot.microservices.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class StressTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(StressTestApplication.class, args);
	}

}
