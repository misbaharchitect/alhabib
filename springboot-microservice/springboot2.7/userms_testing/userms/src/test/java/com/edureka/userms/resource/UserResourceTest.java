package com.edureka.userms.resource;

import com.edureka.userms.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@WebMvcTest(UserResource.class)
class UserResourceTest {
    @Autowired
    private MockMvc mockMvc; // similar to restTemplate
    @MockBean
    UserService userService;

    @Test
    void getAllUsers() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users");
        mockMvc.perform(requestBuilder) // http://localhost:port/users
                .andDo(MockMvcResultHandlers.print()) // print request/response
                // assertions
                .andExpect(MockMvcResultMatchers.status().isOk()) // HttpStatus=200
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[]"));

        Mockito.verify(userService, Mockito.times(1)).getAllUsers(Optional.empty());
    }

    @Test
    void getAllOrders() throws Exception {
        Mockito.when(userService.getAllOrders()).thenReturn("{\"name\":\"abc\"}");
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/orders");
        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print()) // print request/response
                .andExpect(MockMvcResultMatchers.status().isOk()) // HttpStatus=200
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("{\"name\":\"abc\"}"));
        Mockito.verify(userService, Mockito.times(1)).getAllOrders();
    }
}