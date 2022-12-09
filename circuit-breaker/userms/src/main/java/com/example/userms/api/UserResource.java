package com.example.userms.api;

import com.example.userms.model.User;
import com.example.userms.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Sagger url: http://localhost:8081/swagger-ui/index.html
 */
@RestController
public class UserResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private UserRepo repo;

    @GetMapping("/hello")
    public String getHello() {
        return "Hello World!";
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        LOGGER.info("**********************************");
        return repo.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getSingleUsers(@PathVariable Integer id) {
        Optional<User> userById = repo.findById(id);
        if(userById.isPresent()) {
            return ResponseEntity.ok(userById.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) throws URISyntaxException {
        User savedUser = repo.save(user);
        return ResponseEntity.created(new URI(savedUser.getId().toString())).body(savedUser);
    }
}
