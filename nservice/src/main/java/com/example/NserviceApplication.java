package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient

//@EnableResourceServer

public class NserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NserviceApplication.class, args);
	}
}
