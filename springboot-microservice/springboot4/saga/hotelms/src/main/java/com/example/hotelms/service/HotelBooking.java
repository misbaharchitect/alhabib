package com.example.hotelms.service;

import com.example.hotelms.domain.Hotel;
import com.example.hotelms.domain.HotelOrder;
import com.example.hotelms.repo.HotelOrderRepo;
import com.example.hotelms.repo.HotelRepo;
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
        "hotel-topic"
}, groupId = "hotelms")
public class HotelBooking {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelBooking.class);
    
    @Autowired
    private HotelRepo hotelRepo;
    @Autowired
    private HotelOrderRepo hotelOrderRepo;
    @Autowired
    private KafkaTemplate<UUID, BookingEvent> kafkaTemplate;

    @KafkaHandler
    public void handleHotelBookedEvent(@Payload HotelBookingCommand event) {
        LOGGER.info("event to book hotel is received on Kafka");
        Optional<Hotel> hotelFound = hotelRepo.findById(event.getHotelId());

        if (hotelFound.isEmpty()) {
            LOGGER.error("Hotel does not exist (hotelId: {}), please provide valid hotel", event.getHotelId());
            HotelBookingFailedEvent hotelBookingFailedEvent = new HotelBookingFailedEvent();
            hotelBookingFailedEvent.setStatus("InvalidHotelId");
            kafkaTemplate.send("saga-topic", hotelBookingFailedEvent);
            return;
        }

        Hotel hotel = hotelFound.get();
        int numberOfRoomsAvailable = hotel.getNumberOfRoomsAvailable();
        if (numberOfRoomsAvailable < event.getDaysOfStay()) {
            LOGGER.error("Hotel rooms not available for Hotel (hotelId: {}),", event.getHotelId());
            HotelBookingFailedEvent hotelBookingFailedEvent = new HotelBookingFailedEvent();
            hotelBookingFailedEvent.setOrderId(event.getOrderId());
            hotelBookingFailedEvent.setStatus("RoomsNotAvailable");
            kafkaTemplate.send("saga-topic", hotelBookingFailedEvent);
            return;
        }

        hotel.setNumberOfRoomsAvailable(hotel.getNumberOfRoomsAvailable() - event.getDaysOfStay());
        hotelRepo.save(hotel);
        
        HotelOrder hotelOrder = new HotelOrder(UUID.randomUUID(), event.getOrderId(), event.getHotelId(),
                event.getDaysOfStay(), "Completed");
        hotelOrderRepo.save(hotelOrder);

        LOGGER.info("Hotel Booking completed");

        HotelBookedEvent hotelBookedEvent = new HotelBookedEvent();
        hotelBookedEvent.setOrderId(event.getOrderId());
        kafkaTemplate.send("saga-topic", hotelBookedEvent);
    }

    @KafkaHandler
    public void handleHotelCompensateBookingCommand(@Payload HotelCompensateBookingCommand event) {
        LOGGER.info("event to compensate hotel is received on Kafka");

        Optional<Hotel> hotelFound = hotelRepo.findById(event.getHotelId());
        if (hotelFound.isEmpty()) {
            LOGGER.error("hotel not found");
            return;
        }

        Hotel hotel = hotelFound.get();
        hotel.setNumberOfRoomsAvailable(hotel.getNumberOfRoomsAvailable() + event.getDaysOfStay());
        hotelRepo.save(hotel);


        HotelBookingCompensatedEvent hotelBookingCompensatedEvent = new HotelBookingCompensatedEvent();
        hotelBookingCompensatedEvent.setOrderId(event.getOrderId());
        kafkaTemplate.send("saga-topic", hotelBookingCompensatedEvent);
        LOGGER.info("HotelBookingCompensatedEvent is published");
    }


}
