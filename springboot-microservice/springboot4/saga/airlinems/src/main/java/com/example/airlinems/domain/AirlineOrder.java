package com.example.airlinems.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "airline_order")
@DynamicUpdate
public class AirlineOrder {
    @Id
    private UUID airlineId;
    private UUID orderId;
    private UUID userId;
    private LocalDate dateOfArrival;
    private String fromLocation;
    private String toLocation;
    private String status;

    public AirlineOrder() {
    }

    public AirlineOrder(UUID airlineId, UUID orderId, UUID userId, LocalDate dateOfArrival, String fromLocation,
                        String toLocation, String status) {
        this.airlineId = airlineId;
        this.orderId = orderId;
        this.userId = userId;
        this.dateOfArrival = dateOfArrival;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(UUID airlineId) {
        this.airlineId = airlineId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDate getDateOfArrival() {
        return dateOfArrival;
    }

    public void setDateOfArrival(LocalDate dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }
}
