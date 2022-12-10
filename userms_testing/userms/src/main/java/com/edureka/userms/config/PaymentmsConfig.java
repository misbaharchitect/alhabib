package com.edureka.userms.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
//import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import java.util.Arrays;

/*@Configuration
//@EnableOAuth2Client
public class PaymentmsConfig {

    @Bean(name = "oauth2RestTemplate")
    @LoadBalanced
    public OAuth2RestTemplate restTemplate(SpringClientFactory clientFactory) {
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails());
        RibbonLoadBalancerClient ribbonLoadBalancerClient = new RibbonLoadBalancerClient(clientFactory);
        LoadBalancerInterceptor loadBalancerInterceptor = new LoadBalancerInterceptor(ribbonLoadBalancerClient);
        ClientCredentialsAccessTokenProvider accessTokenProvider = new ClientCredentialsAccessTokenProvider();
        accessTokenProvider.setInterceptors(Arrays.asList(loadBalancerInterceptor));
        restTemplate.setAccessTokenProvider(accessTokenProvider);
//         restTemplate.setInterceptors(Arrays.asList(loadBalancerInterceptor));

        return restTemplate;
    }

    public ClientCredentialsResourceDetails resourceDetails() {
        ClientCredentialsResourceDetails clientCredentialsResourceDetails = new ClientCredentialsResourceDetails();
        clientCredentialsResourceDetails.setId("1");
        clientCredentialsResourceDetails.setClientId("userms");
        clientCredentialsResourceDetails.setClientSecret("123");
        clientCredentialsResourceDetails.setAccessTokenUri("http://oauth-server/oauth/token");
//        clientCredentialsResourceDetails.setAccessTokenUri("http://localhost:9000/oauth/token");
        clientCredentialsResourceDetails.setScope(Arrays.asList("read"));
        clientCredentialsResourceDetails.setGrantType("client_credentials");
        return clientCredentialsResourceDetails;
    }
}*/
