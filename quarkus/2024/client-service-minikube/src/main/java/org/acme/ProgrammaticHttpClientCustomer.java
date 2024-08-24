package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public interface ProgrammaticHttpClientCustomer {
    @GET
    public List<Customer> getCustomersFromServiceDatabase();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response addCustomerFromServiceDatabase(Customer customer);
}
