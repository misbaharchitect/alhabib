package com.example.springbootsoapclient.api;

import com.example.springbootsoapclient.config.ReactiveSoapClient;
import https.roytuts_com.userservice.GetUserDetailsRequest;
import https.roytuts_com.userservice.GetUserDetailsResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import java.io.IOException;

@RestController
public class SoapController {
    @GetMapping("/users")
    public Object getUsers() {
        return "Salam Users";
    }

    private ReactiveSoapClient reactiveSoapClient;

    public SoapController(ReactiveSoapClient reactiveSoapClient) {
        this.reactiveSoapClient = reactiveSoapClient;
    }

    @GetMapping(value = "/users-soap/{name}", produces = MediaType.APPLICATION_XML_VALUE)
    public GetUserDetailsResponse callSoap(@PathVariable String name) throws SOAPException, ParserConfigurationException, IOException {
        System.out.println("country : ");
        GetUserDetailsRequest request = new GetUserDetailsRequest();
        request.setName(name);
        return reactiveSoapClient.call( request, null );
    }

}
