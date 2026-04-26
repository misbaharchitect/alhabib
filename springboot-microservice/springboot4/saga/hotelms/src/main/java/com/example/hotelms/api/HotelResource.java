package com.example.hotelms.api;

import com.example.hotelms.domain.Hotel;
import com.example.hotelms.repo.HotelRepo;
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
@RequestMapping("/hotels")
public class HotelResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelResource.class);

    @Autowired
    private HotelRepo repo;
    @GetMapping
    public List<Hotel> getAllHotels() {
        LOGGER.info("Getting all hotels");
        return repo.findAll();
    }

    @GetMapping("/{id}") // return 404 status code if hotel not found else return 200
    public ResponseEntity<Hotel> getHotelById(@PathVariable UUID id) {
        LOGGER.info("Getting hotels by id {}", id);
        Optional<Hotel> hotelFound = repo.findById(id);
        if (hotelFound.isEmpty()) {
            LOGGER.error("Hotel with id {} not found", id);
            return ResponseEntity.notFound().build(); // http status code 404
        }

        LOGGER.info("Hotel with id {} found", id);
        return ResponseEntity.ok(hotelFound.get()); // http status code 200
    }

    @PostMapping // return 201 when new hotel is added
    public ResponseEntity<Hotel> addNewHotel(@RequestBody Hotel hotel) {
        LOGGER.info("Adding new hotel");
        hotel.setId(null);
        Hotel savedHotel = repo.save(hotel);
        // 201 http status code
        return ResponseEntity.created(URI.create(savedHotel.getId().toString())).body(savedHotel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable UUID id) {
        LOGGER.info("Deleting hotel with id {}", id);
        repo.deleteById(id);
        // 204 http status code
        return ResponseEntity.noContent().build();
    }
}
