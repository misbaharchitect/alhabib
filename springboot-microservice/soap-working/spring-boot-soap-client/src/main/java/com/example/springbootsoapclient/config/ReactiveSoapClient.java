package com.example.springbootsoapclient.config;

import https.roytuts_com.userservice.GetUserDetailsRequest;
import https.roytuts_com.userservice.GetUserDetailsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import java.io.IOException;

@Component
public class ReactiveSoapClient {

    private WebClient webClient;
    private String soapServiceUrl="http://localhost:8080/ws";

    public ReactiveSoapClient(WebClient webClient
                              ) {
        this.webClient = webClient;
//        this.soapServiceUrl = soapServiceUrl;
    }

    public GetUserDetailsResponse call(GetUserDetailsRequest getUserDetailsRequest, String soapHeaderContent) throws SOAPException, ParserConfigurationException, IOException {

        SoapEnvelopeRequest soapEnvelopeRequest = new SoapEnvelopeRequest(soapHeaderContent, getUserDetailsRequest);

        GetUserDetailsResponse success = webClient.post()
                .uri(soapServiceUrl)
                .contentType(MediaType.TEXT_XML)
                .body(Mono.just(soapEnvelopeRequest), SoapEnvelopeRequest.class)
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        clientResponse ->
                                clientResponse
                                        .bodyToMono(String.class)
                                        .flatMap(
                                                errorResponseBody ->
                                                        Mono.error(
                                                                new ResponseStatusException(
                                                                        clientResponse.statusCode(),
                                                                        errorResponseBody))))

                .bodyToMono(GetUserDetailsResponse.class)
                .doOnSuccess((GetUserDetailsResponse response) -> {
                    System.out.println("success");
                    System.out.println("users : " + response.getUsers());
                })
                .doOnError(ResponseStatusException.class, error -> {
                    System.out.println("error : " + error);
                })
                .doOnError(Exception.class, (Exception error) -> {
                    System.out.println("error : " + error);
                    error.printStackTrace();
                })
                .block();

        return success;

    }



}
