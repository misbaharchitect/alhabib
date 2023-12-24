package org.acme;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

/**
 * Programmatic client does not need @RegisterRestClient(baseUri = "http://localhost:8085")
 */
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public interface ProgramaticUsermsHttpClient {

    @GET
//    @Path("/customers")
    @Produces(MediaType.APPLICATION_JSON)
    List<Customer> getCustomers();

    @POST
//    @Path("/customers")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response addCustomer(Customer customer);
}
