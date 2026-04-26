package com.example.airlinems.api;

import com.example.airlinems.domain.Airline;
import com.example.airlinems.repo.AirlineRepo;
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
@RequestMapping("/airlines")
public class AirlineResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirlineResource.class);

    @Autowired
    private AirlineRepo repo;
    @GetMapping
    public List<Airline> getAllAirlines() {
        LOGGER.info("Getting all airlines");
        return repo.findAll();
    }

    @GetMapping("/{id}") // return 404 status code if airline not found else return 200
    public ResponseEntity<Airline> getAirlineById(@PathVariable UUID id) {
        LOGGER.info("Getting airlines by id {}", id);
        Optional<Airline> airlineFound = repo.findById(id);
        if (airlineFound.isEmpty()) {
            LOGGER.error("Airline with id {} not found", id);
            return ResponseEntity.notFound().build(); // http status code 404
        }

        LOGGER.info("Airline with id {} found", id);
        return ResponseEntity.ok(airlineFound.get()); // http status code 200
    }

    @PostMapping // return 201 when new airline is added
    public ResponseEntity<Airline> addNewAirline(@RequestBody Airline airline) {
        LOGGER.info("Adding new airline");
        airline.setId(UUID.randomUUID());
        Airline savedAirline = repo.save(airline);
        // 201 http status code
        return ResponseEntity.created(URI.create(savedAirline.getId().toString())).body(savedAirline);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirline(@PathVariable UUID id) {
        LOGGER.info("Deleting airline with id {}", id);
        repo.deleteById(id);
        // 204 http status code
        return ResponseEntity.noContent().build();
    }
}
