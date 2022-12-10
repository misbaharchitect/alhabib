package com.edureka.userms.service;

import com.edureka.userms.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers(Optional<String> name);
    Optional<User> getSingleUser(Long userId);
    User createUser(User user);
    void partiallyUpdateUser(User user);
    void updateUser(User user);
    void deleteUser(Long userId);
    Object getAllOrders();

}
