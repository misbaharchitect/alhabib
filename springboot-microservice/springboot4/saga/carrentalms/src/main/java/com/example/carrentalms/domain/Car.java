package com.example.carrentalms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Entity
@Table(name = "car_data")
@DynamicUpdate
public class Car {
    @Id
    private UUID id;
    @Column(name = "CARTYPE")
    private String carType;
    @Column(name = "CARDAYSAVAILABLE")
    private int carDaysAvailable;

    public Car() {
    }

    public Car(UUID id, String carType, int carDaysAvailable) {
        this.id = id;
        this.carType = carType;
        this.carDaysAvailable = carDaysAvailable;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public int getCarDaysAvailable() {
        return carDaysAvailable;
    }

    public void setCarDaysAvailable(int carDaysAvailable) {
        this.carDaysAvailable = carDaysAvailable;
    }
}
