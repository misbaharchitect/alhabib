package com.example.hotelms.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "hotel_order")
@DynamicUpdate
public class HotelOrder {

    @Id
    private UUID hotelOrderId;
    private UUID orderId;
    private UUID hotelId;
    private int daysOfStay;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HotelOrder() {
    }

    public HotelOrder(UUID hotelOrderId, UUID orderId, UUID hotelId, int daysOfStay, String status) {
        this.hotelOrderId = hotelOrderId;
        this.orderId = orderId;
        this.hotelId = hotelId;
        this.daysOfStay = daysOfStay;
        this.status = status;
    }

    public UUID getHotelOrderId() {
        return hotelOrderId;
    }

    public void setHotelOrderId(UUID hotelOrderId) {
        this.hotelOrderId = hotelOrderId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getHotelId() {
        return hotelId;
    }

    public void setHotelId(UUID hotelId) {
        this.hotelId = hotelId;
    }

    public int getDaysOfStay() {
        return daysOfStay;
    }

    public void setDaysOfStay(int daysOfStay) {
        this.daysOfStay = daysOfStay;
    }
}
