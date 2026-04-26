package com.example.hotelms.repo;

import com.example.hotelms.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HotelRepo extends JpaRepository<Hotel, UUID> {
}
