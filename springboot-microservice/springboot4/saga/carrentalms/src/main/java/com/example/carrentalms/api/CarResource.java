package com.example.carrentalms.api;

import com.example.carrentalms.domain.Car;
import com.example.carrentalms.repo.CarRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cars")
public class CarResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarResource.class);

    @Autowired
    private CarRepo repo;
    @GetMapping
    public List<Car> getAllCars() {
        LOGGER.info("Getting all cars");
        return repo.findAll();
    }

    @GetMapping("/{id}") // return 404 status code if car not found else return 200
    public ResponseEntity<Car> getCarById(@PathVariable UUID id) {
        LOGGER.info("Getting cars by id {}", id);
        Optional<Car> carFound = repo.findById(id);
        if (carFound.isEmpty()) {
            LOGGER.error("Car with id {} not found", id);
            return ResponseEntity.notFound().build(); // http status code 404
        }

        LOGGER.info("Car with id {} found", id);
        return ResponseEntity.ok(carFound.get()); // http status code 200
    }

    @PostMapping // return 201 when new car is added
    public ResponseEntity<Car> addNewCar(@RequestBody Car car) {
        LOGGER.info("Adding new car");
        car.setId(UUID.randomUUID());
        Car savedCar = repo.save(car);
        // 201 http status code
        return ResponseEntity.created(URI.create(savedCar.getId().toString())).body(savedCar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable UUID id) {
        LOGGER.info("Deleting car with id {}", id);
        repo.deleteById(id);
        // 204 http status code
        return ResponseEntity.noContent().build();
    }
}
