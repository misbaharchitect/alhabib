package org.core;

import java.util.UUID;

public class CarBookingCommand extends BookingEvent {
    private UUID carId;
    private int daysForRent;

    public UUID getCarId() {
        return carId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    public int getDaysForRent() {
        return daysForRent;
    }

    public void setDaysForRent(int daysForRent) {
        this.daysForRent = daysForRent;
    }
}
