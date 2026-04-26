package com.example.carrentalms.service;

import com.example.carrentalms.domain.Car;
import com.example.carrentalms.domain.CarOrder;
import com.example.carrentalms.repo.CarOrderRepo;
import com.example.carrentalms.repo.CarRepo;
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
@KafkaListener(topics = {
        "car-topic"
}, groupId = "carrentalms")
public class CarBooking {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarBooking.class);
    
    @Autowired
    private CarRepo carRepo;
    @Autowired
    private CarOrderRepo carOrderRepo;
    @Autowired
    private KafkaTemplate<UUID, BookingEvent> kafkaTemplate;

    @KafkaHandler
    public void handleCarBookedEvent(@Payload CarBookingCommand event) {
        LOGGER.info("event to book car is received on Kafka");
        Optional<Car> carFound = carRepo.findById(event.getCarId());

        if (carFound.isEmpty()) {
            LOGGER.error("Car does not exist (carId: {}), please provide valid car", event.getCarId());
            CarBookingFailedEvent carBookingFailedEvent = new CarBookingFailedEvent();
            carBookingFailedEvent.setOrderId(event.getOrderId());
            carBookingFailedEvent.setStatus("InvalidCarId");
            kafkaTemplate.send("saga-topic", carBookingFailedEvent);
            return;
        }

        Car car = carFound.get();
        int carDaysAvailable = car.getCarDaysAvailable();
        if (carDaysAvailable < event.getDaysForRent()) {
            LOGGER.error("Cars not available for Car (carId: {}),", event.getCarId());
            CarBookingFailedEvent carBookingFailedEvent = new CarBookingFailedEvent();
            carBookingFailedEvent.setOrderId(event.getOrderId());
            carBookingFailedEvent.setStatus("CarsNotAvailable");
            kafkaTemplate.send("saga-topic", carBookingFailedEvent);
            return;
        }

        car.setCarDaysAvailable(car.getCarDaysAvailable() - event.getDaysForRent());
        carRepo.save(car);
        
        CarOrder carOrder = new CarOrder(UUID.randomUUID(), event.getOrderId(), event.getCarId(), event.getDaysForRent());
        carOrderRepo.save(carOrder);

        LOGGER.info("Car Booking completed");

        CarBookedEvent carBookedEvent = new CarBookedEvent();
        carBookedEvent.setOrderId(event.getOrderId());
        kafkaTemplate.send("saga-topic", carBookedEvent);
        LOGGER.info("carms published CarBookedEvent to saga-topic for orderId {}", event.getOrderId());
    }

}
