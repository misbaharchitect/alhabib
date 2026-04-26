package com.example.hotelms.repo;

import com.example.hotelms.domain.HotelOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface HotelOrderRepo extends JpaRepository<HotelOrder, UUID> {
}
