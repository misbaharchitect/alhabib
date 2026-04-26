package org.core;

import java.util.UUID;

public class HotelCompensateBookingCommand extends BookingEvent {
    private UUID hotelBookingId;
    private UUID hotelId;
    private int daysOfStay;

    public int getDaysOfStay() {
        return daysOfStay;
    }

    public void setDaysOfStay(int daysOfStay) {
        this.daysOfStay = daysOfStay;
    }

    public UUID getHotelBookingId() {
        return hotelBookingId;
    }

    public void setHotelBookingId(UUID hotelBookingId) {
        this.hotelBookingId = hotelBookingId;
    }

    public UUID getHotelId() {
        return hotelId;
    }

    public void setHotelId(UUID hotelId) {
        this.hotelId = hotelId;
    }
}
