package org.core;

import java.util.UUID;

public class AirlineBookingCompensatedEvent extends BookingEvent {

    private UUID airlineBookingId;

    public UUID getAirlineBookingId() {
        return airlineBookingId;
    }

    public void setAirlineBookingId(UUID airlineBookingId) {
        this.airlineBookingId = airlineBookingId;
    }

}
