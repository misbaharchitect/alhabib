package com.edureka.userms.service;

import com.edureka.userms.model.OrderTO;
import com.edureka.userms.model.User;
import com.edureka.userms.repository.UserRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final OrderMSClient orderMSClient;

    public UserServiceImpl(UserRepository userRepository, OrderMSClient orderMSClient) {
        this.userRepository = userRepository;
        this.orderMSClient = orderMSClient;
    }

    /**
     * The commandProperties attribute lets you provide
     * additional properties to customize Hystrix.
     *
     * The execution.isolation.thread.timeoutInMilliseconds
     * is used to set the length of the timeout (in
     * milliseconds) of the circuit breaker.
     *
     * Bulkhead:
     * The threadPoolKey attribute defines
     * the unique name of thread pool.
     *
     * The threadPoolProperties attribute lets you define
     * and customize the behavior of the threadPool.
     *
     * The coreSize attribute lets you
     * define the maximum number of
     * threads in the thread pool.
     *
     *  The maxQueueSize lets you define a queue
     * that sits in front of your thread pool and
     * that can queue incoming requests.
     *
     * @param name
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "getUsersFromFallback", commandProperties =
            {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")},
    threadPoolKey = "getAllUsersThreadPool",
    threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "30"),
            @HystrixProperty(name = "maxQueueSize", value = "10")
    })
    public List<User> getAllUsers(Optional<String> name) {
        LOGGER.info("************");
        LOGGER.info("getting all users");
        LOGGER.debug("***");
        LOGGER.error("^^^");
        randomlyRunLong();
        if (name.isPresent()) {
            Optional<List<User>> optionalUserList = userRepository.findByNameContainingIgnoreCase(name.get());
            return optionalUserList.orElse(Collections.emptyList());
        }
        return userRepository.findAll();
    }

    private List<User> getUsersFromFallback(Optional<String> name) {
        final User user = new User();
        user.setId(123456789l);
        user.setName("Fall Back User");

        return Arrays.asList(user);
    }

    private void randomlyRunLong(){
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 1) + 1) + 1;
        if (randomNum==3) sleep();
    }

    private void sleep(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> getSingleUser(Long userId) {
        LOGGER.info("************");
        LOGGER.info("getting single user");
        return userRepository.findById(userId);
    }

    @Override
    public User createUser(User user) {
        User userCreated = userRepository.save(user);
        return userCreated;
    }

    @Override
    public void partiallyUpdateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public OrderTO[] getAllOrders() {
        LOGGER.info("$$$$$$$$$$$$$");
        System.out.println("UserResource");
        OrderTO[] allOrders = orderMSClient.getAllOrders(); // service-1
        System.out.println(allOrders.length);
        OrderTO[] catalogues = orderMSClient.getCatalogues(); // service-2
        System.out.println(catalogues.length);
        // Aggregator Pattern
        OrderTO[] combinedOrders = Stream.of(allOrders, catalogues)
                .flatMap(Stream::of)
                .toArray(OrderTO[]::new);
        return combinedOrders;
    }

}
