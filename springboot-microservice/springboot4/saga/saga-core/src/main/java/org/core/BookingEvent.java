package org.core;

import java.util.UUID;

public abstract class BookingEvent {
    private UUID orderId;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
}
