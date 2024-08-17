package org.acme.db;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jboss.resteasy.reactive.RestQuery;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private final CustomerRepo repo;

    @Inject
    public CustomerResource(CustomerRepo repo) {
        this.repo = repo;
    }

    @GET
    public List<Customer> getAllCustomers() {
        Log.info("Getting all customers from repository");
        return repo.listAll();
    }

    @GET
    @Path("{id}")
    public Customer getSingleCustomersById(@PathParam("id") Long id) {
        Log.infof("Getting single customer with id %s from repository", id);
        return repo.findById(id);
    }

    @POST
    @Transactional
    public Response addCustomers(@RequestBody Customer customer) throws URISyntaxException {
        Log.infof("Add customer to repository");
        repo.persist(customer);
        return Response.created(new URI("customers/"+customer.getId().toString())).entity(customer).build();
    }

    @PUT
    @Path("/{age}")
    @Transactional
    public void updateCustomerByAge(@PathParam("age") int age) {
        Log.infof("Update customer to repository based on age %s", age);
        repo.update("name = 'Brian' where age = ?1", age);
    }

    @DELETE
    @Path("/name")
    @Transactional
    public void deleteByFirstName(@RestQuery String name) {
        Log.infof("Deleting customer to repository based on name %s", name);
        repo.deleteByFirstName(name);
    }

}
