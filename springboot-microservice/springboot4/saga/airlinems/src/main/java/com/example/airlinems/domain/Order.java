package com.example.airlinems.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "order_data")
@DynamicUpdate
public class Order {
    @Id
    private UUID orderId;
    private UUID userId;
    private String status;
    private String airlineBookingStatus;
    private String hotelBookingStatus;
    private String carBookingStatus;
    // fields for airline booking
    private UUID airlineId;
    private int numberOfTicketsRequired;
    private LocalDate dateOfArrival;
    private String fromLocation;
    private String toLocation;
    // fields for hotel booking
    private UUID hotelId;
    private int daysOfStay;
    // fields for car booking
    private UUID carId;
    private int daysForRent;

    private LocalDate createdDate;
    private String createdBy;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getAirlineBookingStatus() {
        return airlineBookingStatus;
    }

    public void setAirlineBookingStatus(String airlineBookingStatus) {
        this.airlineBookingStatus = airlineBookingStatus;
    }

    public String getHotelBookingStatus() {
        return hotelBookingStatus;
    }

    public void setHotelBookingStatus(String hotelBookingStatus) {
        this.hotelBookingStatus = hotelBookingStatus;
    }

    public String getCarBookingStatus() {
        return carBookingStatus;
    }

    public void setCarBookingStatus(String carBookingStatus) {
        this.carBookingStatus = carBookingStatus;
    }

    public UUID getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(UUID airlineId) {
        this.airlineId = airlineId;
    }

    public UUID getHotelId() {
        return hotelId;
    }

    public void setHotelId(UUID hotelId) {
        this.hotelId = hotelId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
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

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getDaysOfStay() {
        return daysOfStay;
    }

    public void setDaysOfStay(int daysOfStay) {
        this.daysOfStay = daysOfStay;
    }

    public int getNumberOfTicketsRequired() {
        return numberOfTicketsRequired;
    }

    public void setNumberOfTicketsRequired(int numberOfTicketsRequired) {
        this.numberOfTicketsRequired = numberOfTicketsRequired;
    }
}
