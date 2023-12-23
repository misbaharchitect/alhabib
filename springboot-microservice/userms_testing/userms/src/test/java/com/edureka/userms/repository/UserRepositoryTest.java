package com.edureka.userms.repository;

import com.edureka.userms.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByName() {
        User user = new User();
        user.setName("abc");
        testEntityManager.persist(user);
        testEntityManager.flush();

        final Optional<List<User>> expectedUser = userRepository.findByNameContainingIgnoreCase("abc");
        assertTrue(expectedUser.isPresent());
        assertEquals(1, expectedUser.get().size());
        assertEquals("abc", expectedUser.get().get(0).getName());
    }

}