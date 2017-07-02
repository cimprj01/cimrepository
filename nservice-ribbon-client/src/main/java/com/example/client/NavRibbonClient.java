package com.example.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.config.NServiceConfiguration;

@RestController
//@RibbonClient(name = "n-service", configuration = NServiceConfiguration.class)
public class NavRibbonClient {

	//@Autowired
	//private RestTemplate restTemplate;
	
    //@Bean
    //public RestTemplate restTemplate() {
      //  return new RestTemplate();
    //}	
	
	/*
	public MessageWrapper<Customer> getCustomer(int id) { 
	    Customer customer = restTemplate.exchange( "http://customer-service/customer/{id}", HttpMethod.GET, null, new ParameterizedTypeReference<Customer>() { }, id).getBody(); 
	    return new MessageWrapper<>(customer, "server called using eureka with rest template"); 
	} 
	*/
	 //@RequestMapping("/helloclient")
	   // public String ribbonClient(@RequestParam(value="name", defaultValue="World") String name) {
	        
		 //return restTemplate.getForObject("http://n-service/hello?name=Nav", String.class);
	    //}	
}
