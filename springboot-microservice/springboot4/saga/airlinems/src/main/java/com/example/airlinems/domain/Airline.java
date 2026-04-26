package com.example.airlinems.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Entity
@Table(name = "airline_data")
@DynamicUpdate
public class Airline {

    @Id
    private UUID id;
    @Column(name = "AIRLINENAME")
    private String airlineName;
    @Column(name = "NUMBEROFTICKETSAVAILABLE")
    private int numberOfTicketsAvailable;

    public Airline() {
    }

    public Airline(UUID id, String airlineName, int numberOfTicketsAvailable) {
        this.id = id;
        this.airlineName = airlineName;
        this.numberOfTicketsAvailable = numberOfTicketsAvailable;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public int getNumberOfTicketsAvailable() {
        return numberOfTicketsAvailable;
    }

    public void setNumberOfTicketsAvailable(int numberOfTicketsAvailable) {
        this.numberOfTicketsAvailable = numberOfTicketsAvailable;
    }
}
