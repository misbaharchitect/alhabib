package org.core;

import java.util.UUID;

public class CarBookingFailedEvent extends BookingEvent {
    private UUID carBookingId;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public UUID getCarBookingId() {
        return carBookingId;
    }

    public void setCarBookingId(UUID carBookingId) {
        this.carBookingId = carBookingId;
    }

}
