package com.edureka.userms.resource;

import com.edureka.userms.model.User;
import com.edureka.userms.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
class UserResourceIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private UserRepository userRepository;

    @Test
    void getAllUsers() throws Exception {
//        final ResponseEntity<List> response = testRestTemplate.getForEntity("http://localhost:" + port + "/users", List.class);
        final ResponseEntity<List<User>> response = testRestTemplate.exchange("http://localhost:" + port +
                "/users", HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});

        // Assertions
        MatcherAssert.assertThat(response.getStatusCode(), CoreMatchers.equalTo(HttpStatus.OK));
        final int totalUsers = userRepository.findAll().size(); // Direct call to DB
        MatcherAssert.assertThat(response.getBody().size(), CoreMatchers.equalTo(totalUsers));
        final List<User> userList = response.getBody();
        assertNotNull(userList);
    }

    @Test
    void saveUser() throws Exception {
        final User user = new User();
        user.setName("Test User");
        final HttpEntity<User> userHttpEntity = new HttpEntity<>(user);
        final ResponseEntity<User> response = testRestTemplate.postForEntity("http://localhost:" + port + "/users",
                userHttpEntity, User.class);
        MatcherAssert.assertThat(response.getStatusCode(), CoreMatchers.equalTo(HttpStatus.CREATED));

        // Assertions
        final Optional<List<User>> test_user = userRepository.findByNameContainingIgnoreCase("Test User");
        assertTrue(test_user.isPresent());
        assertEquals(test_user.get().size(), 1);
        assertEquals(test_user.get().get(0).getId(), 5);
        assertEquals(test_user.get().get(0).getName(), "Test User");
    }

}