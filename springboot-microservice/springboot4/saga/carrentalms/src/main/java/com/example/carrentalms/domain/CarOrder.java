package com.example.carrentalms.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Entity
@Table(name = "car_order")
@DynamicUpdate
public class CarOrder {

    @Id
    private UUID carOrderId;
    private UUID orderId;
    private UUID carId;
    private int daysForRent;

    public CarOrder() {
    }

    public CarOrder(UUID carOrderId, UUID orderId, UUID carId, int daysForRent) {
        this.carOrderId = carOrderId;
        this.orderId = orderId;
        this.carId = carId;
        this.daysForRent = daysForRent;
    }

    public UUID getCarOrderId() {
        return carOrderId;
    }

    public void setCarOrderId(UUID carOrderId) {
        this.carOrderId = carOrderId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

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
