package com.example.airlinems.api;

import com.example.airlinems.domain.Order;
import com.example.airlinems.repo.OrderRepo;
import com.example.airlinems.service.AirlineBookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class AirlineBookingApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirlineBookingApi.class);

    @Autowired
    private AirlineBookingService service;
    @Autowired
    private OrderRepo orderRepo;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void initiateBooking(@RequestBody Order order) {
        LOGGER.info("Initiate airline booking");
        order.setOrderId(UUID.randomUUID());
        orderRepo.save(order);
        service.publishAirlineBooking(order);
    }

}
