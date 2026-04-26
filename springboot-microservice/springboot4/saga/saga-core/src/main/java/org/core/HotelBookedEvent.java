package org.core;

import java.util.UUID;

public class HotelBookedEvent extends BookingEvent {

    private UUID hotelBookingId;

    public UUID getHotelBookingId() {
        return hotelBookingId;
    }

    public void setHotelBookingId(UUID hotelBookingId) {
        this.hotelBookingId = hotelBookingId;
    }

}
