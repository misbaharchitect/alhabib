package com.example.usermsclient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@RestController
@EnableHystrix
public class UsermsClientApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(UsermsClientApplication.class);

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/users-client")
	@HystrixCommand(fallbackMethod = "getUsersFromFallback",
	commandProperties = {
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
	})
	public List getUsersFromUserms() {
		LOGGER.info("about to call userms");
		return restTemplate.getForObject("http://userms/users", List.class);
//		return restTemplate.getForObject("http://localhost:8081/users", List.class);
	}

	private List getUsersFromFallback() {
		return Arrays.asList("a", "b", "c");
	}

	public static void main(String[] args) {
		SpringApplication.run(UsermsClientApplication.class, args);
	}

}
