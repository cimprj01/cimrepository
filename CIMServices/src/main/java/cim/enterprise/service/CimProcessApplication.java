package cim.enterprise.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import cim.enterprise.data.Tool;
import cim.enterprise.data.repository.ToolRepository;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "cim.enterprise.data.repository")
@SpringBootApplication(scanBasePackages = { "cim.enterprise" })
@EntityScan(basePackages={"cim.enterprise.data"})
@EnableEurekaClient

public class CimProcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(CimProcessApplication.class, args);
	}
}
