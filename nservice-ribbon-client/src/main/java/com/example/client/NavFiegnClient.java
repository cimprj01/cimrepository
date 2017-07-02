package com.example.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@RestController
@EnableFeignClients

public class NavFiegnClient {
	
	@Autowired
    NServiceClient client;

	@Autowired
	private RestTemplate restTemplate;
	
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }	
	
	@RequestMapping("/hellofeign")
    public String fiegnClient(@RequestParam(value="name", defaultValue="World") String name) {
        
	 //return restTemplate.getForObject("http://n-service/hello?name=Nav", String.class);
		System.out.println("Passed Value : " + name);
		return client.hello(name);
    }	

	  @FeignClient("n-service")
	    interface NServiceClient {
	        @RequestMapping("/hello")
	        String hello(@RequestParam(value="name", defaultValue="World")String name);
	    }
	

}
