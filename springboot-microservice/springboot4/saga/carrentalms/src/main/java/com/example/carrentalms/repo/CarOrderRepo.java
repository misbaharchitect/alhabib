package com.example.carrentalms.repo;

import com.example.carrentalms.domain.CarOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CarOrderRepo extends JpaRepository<CarOrder, UUID> {
}
