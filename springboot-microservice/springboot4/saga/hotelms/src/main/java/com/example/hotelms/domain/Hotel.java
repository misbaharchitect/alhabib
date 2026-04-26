package com.example.hotelms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Entity
@Table(name = "hotel_data")
@DynamicUpdate
public class Hotel {
    @Id
    private UUID id;
    @Column(name = "HOTELNAME")
    private String hotelName;
    @Column(name = "NUMBEROFROOMSAVAILABLE")
    private int numberOfRoomsAvailable;

    public Hotel() {
    }

    public Hotel(UUID id, String hotelName, int numberOfRoomsAvailable) {
        this.id = id;
        this.hotelName = hotelName;
        this.numberOfRoomsAvailable = numberOfRoomsAvailable;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getNumberOfRoomsAvailable() {
        return numberOfRoomsAvailable;
    }

    public void setNumberOfRoomsAvailable(int numberOfRoomsAvailable) {
        this.numberOfRoomsAvailable = numberOfRoomsAvailable;
    }
}
