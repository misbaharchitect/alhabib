package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * Programmatic client does not need @RegisterRestClient(baseUri = "http://localhost:8085")
 */
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public interface ProgramaticHttpClientCustomer {

    @GET
    List<Customer> getCustomers();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response addCustomer(Customer customer);
}
