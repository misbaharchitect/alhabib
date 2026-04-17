package com.example.orderms_client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    private final OrdermsService ordermsService;

    public ClientController(OrdermsService ordermsService) {
        this.ordermsService = ordermsService;
    }

    @GetMapping("/call-orderms")
    public String callOrderms() {
        return ordermsService.callOrdermsHelloWorld();
    }
}
