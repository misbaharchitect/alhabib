package com.example.usermswithdb.controller;

import com.example.usermswithdb.model.User;
import com.example.usermswithdb.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class UserResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        LOGGER.info("Getting All Users");
        return userRepo.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getSingleUser(@PathVariable Integer id) {
        LOGGER.info("Getting Single User: {}", id);
        Optional<User> userFound = userRepo.findById(id);
        if(userFound.isPresent()) {
            return ResponseEntity.ok(userFound.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User userToBeSaved) throws URISyntaxException {
        LOGGER.info("Saving User");
        // userToBeSaved.setId(null);
        User savedUser = userRepo.save(userToBeSaved);
        LOGGER.info("Saved User: {}", savedUser.getId());

        return ResponseEntity.created(new URI(savedUser.getId().toString())).body(savedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userRepo.deleteById(id);
        return ResponseEntity.ok().build();
        /*Optional<User> userFound = userRepo.findById(id);
        if(userFound.isPresent()) {
            userRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();*/
    }


}
