package org.core;

import java.util.UUID;

public class HotelBookingCompensatedEvent extends BookingEvent {
    private UUID hotelBookingId;
    public UUID getHotelBookingId() {
        return hotelBookingId;
    }

    public void setHotelBookingId(UUID hotelBookingId) {
        this.hotelBookingId = hotelBookingId;
    }

}
