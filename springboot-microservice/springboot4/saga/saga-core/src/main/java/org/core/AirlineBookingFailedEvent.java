package org.core;

public class AirlineBookingFailedEvent extends BookingEvent {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
