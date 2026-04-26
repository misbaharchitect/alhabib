package org.core;

import java.util.UUID;

public class HotelBookingCommand extends BookingEvent {

    private int daysOfStay;
    private UUID hotelId;

    public int getDaysOfStay() {
        return daysOfStay;
    }

    public void setDaysOfStay(int daysOfStay) {
        this.daysOfStay = daysOfStay;
    }

    public UUID getHotelId() {
        return hotelId;
    }

    public void setHotelId(UUID hotelId) {
        this.hotelId = hotelId;
    }
}
