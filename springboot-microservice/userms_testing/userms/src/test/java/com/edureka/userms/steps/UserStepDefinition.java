package com.edureka.userms.steps;

import com.edureka.userms.model.User;
import com.edureka.userms.repository.UserRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
public class UserStepDefinition {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    private MvcResult mvcResult;
    private ResultActions resultActions;

    @Given("I have no data in users table")
    public void cleanupUsersTable() {
        userRepository.deleteAll();
    }

    @And("^I add a new user with name (\\w+)$")
    public void addNewUser(String userName) {
        System.out.println("UserName: " + userName);
        final User user = new User();
        user.setName(userName);
        userRepository.save(user);
    }

    @When("client calls /users GET api")
    public void getAllUsers() throws Exception {
        System.out.println("111111");
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users");
        resultActions = mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print());
    }

    @Then("client receives following users")
    public void verifyAllUsers(DataTable dataTable) throws Exception {
        System.out.println("22222");
        final List<String> userList = dataTable.asList();
        Assert.assertEquals("userName", userList.get(0));
        resultActions.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(userList.get(1))))
        ;
    }

}
