package com.example.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@RefreshScope
public class ConfigClientApplication {
	@Value("${my-property}")
	private String myProperty;
	@Value("${your-property}")
	private String yourProperty;
	@Value("${paymentPassword}")
	private String paymentPassword;
	@Value("${common.property}")
	private String commonProperty;

	@GetMapping("/properties")
	public Map<String, String> getProperties() {
		HashMap<String, String> properties = new HashMap<>();
		properties.put("myProperty", myProperty);
		properties.put("yourProperty", yourProperty);
		properties.put("paymentPassword", paymentPassword);
		properties.put("commonProperty", commonProperty);

		return properties;
	}

	public static void main(String[] args) {
		SpringApplication.run(ConfigClientApplication.class, args);
	}

}
