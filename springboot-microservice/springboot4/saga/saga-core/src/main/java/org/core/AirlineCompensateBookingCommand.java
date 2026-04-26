package org.core;

import java.util.UUID;

public class AirlineCompensateBookingCommand extends BookingEvent {
    private UUID airlineBookingId;
    private UUID airlineId;
    private int numberOfTicketsRequired;

    public int getNumberOfTicketsRequired() {
        return numberOfTicketsRequired;
    }

    public void setNumberOfTicketsRequired(int numberOfTicketsRequired) {
        this.numberOfTicketsRequired = numberOfTicketsRequired;
    }

    public UUID getAirlineBookingId() {
        return airlineBookingId;
    }

    public void setAirlineBookingId(UUID airlineBookingId) {
        this.airlineBookingId = airlineBookingId;
    }

    public UUID getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(UUID airlineId) {
        this.airlineId = airlineId;
    }
}
