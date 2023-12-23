//package com.example.apigateway;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
//import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//import javax.crypto.spec.SecretKeySpec;
//
//@Configuration
//@EnableWebFluxSecurity
//public class ResourceServerConfig {
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
////        httpSecurity.authorizeExchange().pathMatchers(HttpMethod.GET, "/actuator/**").hasRole("admin");
////        httpSecurity.authorizeExchange().pathMatchers("/oauth-server/**").permitAll().anyExchange().authenticated();
//        httpSecurity.authorizeExchange().anyExchange().authenticated()
//                .and()
//                .oauth2ResourceServer().jwt();
//
//        return httpSecurity.build();
//    }
//
//    @Bean
//    public ReactiveJwtDecoder reactiveJwtDecoder() {
//        byte[] bytes = "123456789012345678901234567890AB".getBytes();
//        SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, 0, bytes.length, "AES");
//        return NimbusReactiveJwtDecoder.withSecretKey(secretKeySpec).build();
//    }
//}
