package org.acme.rest;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.acme.entity.Customer;
import org.acme.repo.CustomerRepo;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("customers")
@Produces(APPLICATION_JSON)
public class CustomerResource {
    
    private final CustomerRepo customerRepo;

    @Inject
    public CustomerResource(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @GET
    public List<Customer> allCustomer() {
        Log.info("Getting all customers");
        /*User john = new User();
        john.setId(1);
        john.setName("John");

        return List.of(john, john);*/
        return customerRepo.listAll();
    }

    @GET
    @Path("id/{id}")
    public Customer findByName(@PathParam("id") Long id) {
        Log.infof("Getting one customer by name %s", id);
        return customerRepo.findById(id);
    }

    @GET
    @Path("name/{name}")
    public Customer findByName(@PathParam("name") String name) {
        Log.infof("Getting one customer by name %s", name);
        return customerRepo.findByName(name);
    }

    @GET
    @Path("age/{age}")
    public List<Customer> findByName(@PathParam("age") int age) {
        Log.info("Getting all customers");
        return customerRepo.findByAge(age);
    }
    @DELETE
    @Path("name/{name}")
    @Transactional
    public void deleteByName(@PathParam("name") String name) {
        Log.info("Getting all customers");
        customerRepo.deleteByName(name);
    }

    @PUT
    @Path("age/{age}")
    @Transactional
    public void updateNameByAge(@PathParam("age") int age) {
        Log.info("Getting all customers");
        customerRepo.update("name = 'John' where age = ?1", age);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Transactional
    public Response newUser(@RequestBody Customer customerToPersist) throws URISyntaxException {
        Log.infof("save customerToPersist %s", customerToPersist);
        customerToPersist.setId(null);
        customerRepo.persist(customerToPersist);
        return Response.created(new URI(customerToPersist.getName())).entity(customerToPersist).build();
    }
}
