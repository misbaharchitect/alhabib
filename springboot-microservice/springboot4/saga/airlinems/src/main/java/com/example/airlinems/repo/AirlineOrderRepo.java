package com.example.airlinems.repo;

import com.example.airlinems.domain.Airline;
import com.example.airlinems.domain.AirlineOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AirlineOrderRepo extends JpaRepository<AirlineOrder, UUID> {
}
