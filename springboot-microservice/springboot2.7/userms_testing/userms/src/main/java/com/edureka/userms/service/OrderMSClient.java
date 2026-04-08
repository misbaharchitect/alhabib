package com.edureka.userms.service;

import com.edureka.userms.model.OrderTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderMSClient {
    private final RestTemplate restTemplate;

    public OrderMSClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


//    public OrderTO[] getAllOrders() {
//        System.out.println("Aggregate: method");
////        OrderTO[] orders = getOrders();
////        return orders;
//        /*OrderTO[] catalogues = getCatalogues();
//        OrderTO[] combinedOrders = Stream.of(orders, catalogues)
//                .flatMap(Stream::of)
//                .toArray(OrderTO[]::new);
//        return combinedOrders;*/
//        OrderTO[] allOrders2 = getAllOrders2();
//        System.out.println("Aggregate: After orderms call");
//        return allOrders2;
//    }

    @HystrixCommand(fallbackMethod = "getAllOrdersFromFallBack")
    public OrderTO[] getAllOrders() {
        System.out.println("****************");
        System.out.println("Getting all orders from Orderms");
        OrderTO[] forObject = restTemplate.getForObject("http://orderms/orders", OrderTO[].class);

//        restTemplate.getForObject("http://orderms-spc/orders", OrderTO[].class);
//        System.out.println("forObject[0].getName(): " + forObject[0].getName());
//        System.out.println("forObject[0].getId(): " + forObject[0].getOrderId());
        return forObject;
    }

    public OrderTO[] getAllOrdersFromFallBack() {
        // cache
        System.out.println("inside fallback");
        OrderTO[] orderTOArray = new OrderTO[2];
        orderTOArray[0] = new OrderTO(88L, "Eighty-eight");
        orderTOArray[1] = new OrderTO(99L, "Ninety-Nine");

        return orderTOArray;
    }

    @HystrixCommand(fallbackMethod = "getAllCataloguesFromFallBack")
    public OrderTO[] getCatalogues() {
        System.out.println("****************");
        System.out.println("SPC-Getting all orders from Orderms-SPC");
//        return getAllCataloguesFromFallBack();
        return restTemplate.getForObject("http://orderms-spc/orders", OrderTO[].class);
    }


    public OrderTO[] getAllCataloguesFromFallBack() {
        // cache
        OrderTO[] orderTOArray = new OrderTO[2];
        orderTOArray[0] = new OrderTO(66L, "Sixty-six");
        orderTOArray[1] = new OrderTO(55L, "Fifty-five");

        return orderTOArray;
    }
}
