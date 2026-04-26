package com.example.airlinems.service;

import com.example.airlinems.api.AirlineBookingApi;
import com.example.airlinems.domain.Airline;
import com.example.airlinems.domain.AirlineOrder;
import com.example.airlinems.domain.Order;
import com.example.airlinems.repo.AirlineOrderRepo;
import com.example.airlinems.repo.AirlineRepo;
import com.example.airlinems.repo.OrderRepo;
import org.core.AirlineBookedEvent;
import org.core.AirlineBookingFailedEvent;
import org.core.BookingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AirlineBookingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AirlineBookingApi.class);

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private AirlineRepo airlineRepo;

    @Autowired
    private AirlineOrderRepo airlineOrderRepo;
    @Autowired
    private KafkaTemplate<UUID, BookingEvent> kafkaTemplate;

    public void publishAirlineBooking(Order order) {
        LOGGER.info("Saving order to database");

        // check if airline tickets are available
        Optional<Airline> airlineFound = airlineRepo.findById(order.getAirlineId());
        if (airlineFound.isEmpty()) {
            LOGGER.error("Airline does not exist (airlineId: {}), please provide valid airline", order.getAirlineId());

            AirlineBookingFailedEvent airlineBookingFailedEvent = new AirlineBookingFailedEvent();
            airlineBookingFailedEvent.setStatus("InvalidAirlineId");
            airlineBookingFailedEvent.setOrderId(order.getOrderId());

            kafkaTemplate.send("saga-topic", airlineBookingFailedEvent);
            return;
        }

        Airline airline = airlineFound.get();
        int numberOfTicketsAvailable = airline.getNumberOfTicketsAvailable();
        if (numberOfTicketsAvailable < order.getNumberOfTicketsRequired()) {
            LOGGER.error("Tickets not available for Airline (airlineId: {}),", order.getAirlineId());
            AirlineBookingFailedEvent airlineBookingFailedEvent = new AirlineBookingFailedEvent();
            airlineBookingFailedEvent.setStatus("TicketsNotAvailable");
            airlineBookingFailedEvent.setOrderId(order.getOrderId());

            kafkaTemplate.send("saga-topic", airlineBookingFailedEvent);
            return;
        }

        airline.setNumberOfTicketsAvailable(airline.getNumberOfTicketsAvailable() - order.getNumberOfTicketsRequired());
        airlineRepo.save(airline);

        AirlineOrder airlineOrder = new AirlineOrder(UUID.randomUUID(), order.getOrderId(), order.getUserId(),
                order.getDateOfArrival(), order.getFromLocation(), order.getToLocation(), "Success");
        AirlineOrder savedAirlineOrder = airlineOrderRepo.save(airlineOrder);
        LOGGER.info("AirlineOrder saved with id: {} for orderId {}", savedAirlineOrder.getAirlineId(), order.getOrderId());

        order.setAirlineBookingStatus("Complete"); // Pending, Complete, Failed
        order.setStatus("InProgress"); // InProgress, AirlineBooked, AirlineBookingFailed, HotelBooked etc.
        Order savedOrder = orderRepo.save(order);
        LOGGER.info("Order {} Saved successfully to database", savedOrder.getOrderId());

        AirlineBookedEvent airlineBookedEvent = new AirlineBookedEvent();
        airlineBookedEvent.setAirlineBookingId(order.getAirlineId());
        airlineBookedEvent.setOrderId(order.getOrderId());

        kafkaTemplate.send("saga-topic", airlineBookedEvent);
        LOGGER.info("airlineBookedEvent successfully published to airline-topic");
    }

}
