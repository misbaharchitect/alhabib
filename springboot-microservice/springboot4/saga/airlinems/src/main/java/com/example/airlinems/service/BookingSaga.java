package com.example.airlinems.service;

import com.example.airlinems.domain.Airline;
import com.example.airlinems.domain.Order;
import com.example.airlinems.repo.AirlineOrderRepo;
import com.example.airlinems.repo.AirlineRepo;
import com.example.airlinems.repo.OrderRepo;
import org.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@KafkaListener(topics = {"saga-topic", "airline-topic"},
        groupId = "saga")
public class BookingSaga {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingSaga.class);
    private final String airlineTopic = "airline-topic"; // used as saga-topic also
    private final String hotelTopic = "hotel-topic";
    private final String carTopic = "car-topic";

    @Autowired
    private KafkaTemplate<UUID, BookingEvent> kafkaTemplate;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private AirlineRepo airlineRepo;
    @Autowired
    private AirlineOrderRepo airlineOrderRepo;

    @KafkaHandler
    public void handleAirlineBookedEvent(@Payload AirlineBookedEvent event) {
        LOGGER.info("Received AirlineBookedEvent and publishing to hotelTopic");
        Optional<Order> orderFound = orderRepo.findById(event.getOrderId());
        if (orderFound.isEmpty()) {
            LOGGER.error("orderId {} does not exist", event.getOrderId());
            return;
        }
        Order order = orderFound.get();
        order.setStatus("AirlineBooked");
        order.setAirlineBookingStatus("Complete");
        orderRepo.save(order);
        HotelBookingCommand hotelBookingCommand = new HotelBookingCommand();
        hotelBookingCommand.setOrderId(order.getOrderId());
        hotelBookingCommand.setHotelId(order.getHotelId());
        hotelBookingCommand.setDaysOfStay(order.getDaysOfStay());
        kafkaTemplate.send(hotelTopic, event.getOrderId(), hotelBookingCommand);
        LOGGER.info("Published AirlineBookedEvent to hotelTopic");
    }

    @KafkaHandler
    public void handleHotelBookedEvent(@Payload HotelBookedEvent event) {
        LOGGER.info("Received HotelBookedEvent and publishing to carTopic");
        Optional<Order> orderFound = orderRepo.findById(event.getOrderId());
        if (orderFound.isEmpty()) {
            LOGGER.error("orderId {} does not exist", event.getOrderId());
            return;
        }
        Order order = orderFound.get();
        order.setStatus("HotelBooked");
        order.setHotelBookingStatus("Complete");
        orderRepo.save(order);
        CarBookingCommand carBookingCommand = new CarBookingCommand();
        carBookingCommand.setOrderId(order.getOrderId());
        carBookingCommand.setCarId(order.getCarId());
        carBookingCommand.setDaysForRent(order.getDaysForRent());
        kafkaTemplate.send(carTopic, event.getOrderId(), carBookingCommand);
        LOGGER.info("Published CarBookingCommand to carTopic");
    }

    @KafkaHandler
    public void handleCarBookedEvent(@Payload CarBookedEvent event) {
        LOGGER.info("Received CarBookedEvent and update in database");
        Optional<Order> orderFound = orderRepo.findById(event.getOrderId());
        if (orderFound.isEmpty()) {
            LOGGER.error("orderId {} does not exist", event.getOrderId());
            return;
        }
        Order order = orderFound.get();
        order.setStatus("AllBooked");
        order.setCarBookingStatus("Complete");
        orderRepo.save(order);
        LOGGER.info("Saga ends successfully");
    }

    @KafkaHandler
    public void handleAirlineBookingFailedEvent(@Payload AirlineBookingFailedEvent event) {
        LOGGER.error("AirlineBookingFailedEvent is received");
        Optional<Order> orderFound = orderRepo.findById(event.getOrderId());
        if (orderFound.isEmpty()) {
            LOGGER.error("orderId {} does not exist", event.getOrderId());
            return;
        }
        Order order = orderFound.get();
        order.setStatus("AirlineBookingFailed");
        order.setAirlineBookingStatus(event.getStatus());
        orderRepo.save(order);
    }

    @KafkaHandler
    public void handleHotelBookingFailedEvent(@Payload HotelBookingFailedEvent event) {
        LOGGER.error("HotelBookingFailedEvent is received");
        Optional<Order> orderFound = orderRepo.findById(event.getOrderId());
        if (orderFound.isEmpty()) {
            LOGGER.error("orderId {} does not exist", event.getOrderId());
            return;
        }
        Order order = orderFound.get();
        order.setStatus("HotelBookingFailed");
        order.setHotelBookingStatus(event.getStatus());
        orderRepo.save(order);

        AirlineCompensateBookingCommand airlineCompensateBookingCommand = new AirlineCompensateBookingCommand();
        airlineCompensateBookingCommand.setOrderId(order.getOrderId());
        airlineCompensateBookingCommand.setAirlineId(order.getAirlineId());
        airlineCompensateBookingCommand.setNumberOfTicketsRequired(order.getNumberOfTicketsRequired());
        kafkaTemplate.send(airlineTopic, airlineCompensateBookingCommand);
        LOGGER.error("Published AirlineCompensateBookingCommand");
    }

    @KafkaHandler
    public void handleCarBookingFailedEvent(@Payload CarBookingFailedEvent event) {
        LOGGER.error("CarBookingFailedEvent is received");
        Optional<Order> orderFound = orderRepo.findById(event.getOrderId());
        if (orderFound.isEmpty()) {
            LOGGER.error("orderId {} does not exist", event.getOrderId());
            return;
        }
        Order order = orderFound.get();
        order.setStatus("CarBookingFailed");
        order.setCarBookingStatus(event.getStatus());
        orderRepo.save(order);
        LOGGER.error("Compensate HotelBooking for orderId {}", order.getOrderId());

        HotelCompensateBookingCommand hotelCompensateBookingCommand = new HotelCompensateBookingCommand();
        hotelCompensateBookingCommand.setOrderId(order.getOrderId());
        hotelCompensateBookingCommand.setHotelId(order.getHotelId());
        hotelCompensateBookingCommand.setDaysOfStay(order.getDaysOfStay());
        kafkaTemplate.send(hotelTopic, hotelCompensateBookingCommand);
    }

    @KafkaHandler
    public void handleAirlineCompensateBookingCommand(@Payload AirlineCompensateBookingCommand event) {
        LOGGER.error("AirlineCompensateBookingCommand is received");
        Optional<Order> orderFound = orderRepo.findById(event.getOrderId());
        if (orderFound.isEmpty()) {
            LOGGER.error("orderId {} does not exist", event.getOrderId());
            return;
        }

        Optional<Airline> airlineFound = airlineRepo.findById(event.getAirlineId());
        if (airlineFound.isEmpty()) {
            LOGGER.error("airline not found");
            return;
        }

        Airline airline = airlineFound.get();
        airline.setNumberOfTicketsAvailable(airline.getNumberOfTicketsAvailable() + event.getNumberOfTicketsRequired());
        airlineRepo.save(airline);

        AirlineBookingCompensatedEvent airlineBookingCompensatedEvent = new AirlineBookingCompensatedEvent();
        airlineBookingCompensatedEvent.setOrderId(event.getOrderId());
        kafkaTemplate.send(airlineTopic, airlineBookingCompensatedEvent);

        LOGGER.error("Completed AirlineCompensateBookingCommand");
    }

    @KafkaHandler
    public void handleHotelBookingCompensatedEvent(@Payload HotelBookingCompensatedEvent event) {
        LOGGER.info("HotelBookingCompensatedEvent is received");
        Optional<Order> orderFound = orderRepo.findById(event.getOrderId());
        if (orderFound.isEmpty()) {
            LOGGER.error("orderId {} does not exist", event.getOrderId());
            return;
        }
        Order order = orderFound.get();
        order.setStatus("HotelBookingCompensated");
        order.setHotelBookingStatus("Compensated");
        orderRepo.save(order);
        LOGGER.error("Compensate AirlineBooking for orderId {}", order.getOrderId());
        AirlineCompensateBookingCommand airlineCompensateBookingCommand = new AirlineCompensateBookingCommand();
        airlineCompensateBookingCommand.setOrderId(order.getOrderId());
        airlineCompensateBookingCommand.setAirlineId(order.getAirlineId());
        airlineCompensateBookingCommand.setNumberOfTicketsRequired(order.getNumberOfTicketsRequired());

        kafkaTemplate.send(airlineTopic, airlineCompensateBookingCommand);
    }

    @KafkaHandler
    public void handleAirlineBookingCompensatedEvent(@Payload AirlineBookingCompensatedEvent event) {
        LOGGER.info("AirlineBookingCompensatedEvent is received");
        Optional<Order> orderFound = orderRepo.findById(event.getOrderId());
        if (orderFound.isEmpty()) {
            LOGGER.error("orderId {} does not exist", event.getOrderId());
            return;
        }
        Order order = orderFound.get();
        order.setStatus("AllBookingCompensated");
        order.setAirlineBookingStatus("Compensated");

        orderRepo.save(order);
        LOGGER.error("All bookings compensated for orderId {}", order.getOrderId());
    }
}
