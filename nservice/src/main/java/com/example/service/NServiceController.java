package com.example.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableHystrix
public class NServiceController {
	
	
	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/hello")
    public NObj greeting(@RequestParam(value="name", defaultValue="World") String name) {
    	
    	System.out.println("n-service  I am called + " + name);
    	
        return new NObj(counter.incrementAndGet(),
                            String.format(template, name));
    }
}
