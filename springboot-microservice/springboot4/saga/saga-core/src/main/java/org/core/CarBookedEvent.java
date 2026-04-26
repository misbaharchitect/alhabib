package org.core;

import java.util.UUID;

public class CarBookedEvent extends BookingEvent {
    private UUID carBookingId;
//    private String carModel;

    public UUID getCarBookingId() {
        return carBookingId;
    }

    public void setCarBookingId(UUID carBookingId) {
        this.carBookingId = carBookingId;
    }

}
