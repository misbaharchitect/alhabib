package com.edureka.userms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RefreshScope
//@RestController
@EnableHystrix
//@EnableCircuitBreaker
//@EnableOAuth2Client
//@EnableResourceServer
public class UsermsApplication /*extends ResourceServerConfigurerAdapter*/ {

	/*@Autowired
	@Qualifier("oauth2RestTemplate")
	@LoadBalanced
	private OAuth2RestTemplate myOauth2RestTemplate;*/

	/*@GetMapping("/hello")
	public String callPaymentms() {
		System.out.println("***********************");
		System.out.println("***********************");
		System.out.println("***********************");
		String result = myOauth2RestTemplate.getForObject("http://paymentms/hello", String.class);
		return result;
	}

	@GetMapping("/secure/hello")
	public String getSecureData() {
		System.out.println("***********************");
		System.out.println("***********************");
		String result = myOauth2RestTemplate.getForObject("http://paymentms/secure/hello", String.class);
		return result;
	}

	@PostMapping("/secure/hello")
	public String postSecurePaymentms() {
		System.out.println("secure post");
		String result = myOauth2RestTemplate.postForObject("http://paymentms/secure/hello", String.class, String.class);
		return result;
	}

	@DeleteMapping("/secure/hello")
	public void deleteSecurePaymentms() {
		System.out.println("secure delete");
		myOauth2RestTemplate.delete("http://paymentms/secure/hello");
	}*/


	/*@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.authorizeRequests().antMatchers("/h2-console").permitAll();
		http.requestMatchers().antMatchers("/**")
				.and()
				.authorizeRequests()
				.anyRequest().permitAll();
	}*/


	public static void main(String[] args) {
		SpringApplication.run(UsermsApplication.class, args);
	}

}
