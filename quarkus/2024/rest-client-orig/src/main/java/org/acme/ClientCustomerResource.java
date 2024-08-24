package org.acme;


import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Path("client-customer")
@Produces(MediaType.APPLICATION_JSON)
public class ClientCustomerResource {


    private final ProgramaticHttpClientCustomer httpClient;

    public ClientCustomerResource() throws MalformedURLException {
        httpClient = RestClientBuilder.newBuilder()
                .baseUrl(new URL("http://localhost:8081"))
                .connectTimeout(500, TimeUnit.MILLISECONDS)
                .readTimeout(1200, TimeUnit.MILLISECONDS)
                .build(ProgramaticHttpClientCustomer.class);
    }

    @GET
    @Path("customers")
    public List<Customer> getUsersFromUserms() {
        Log.info("Getting customer from another service");
        return httpClient.getCustomers();
    }

    @POST
    @Path("customers")
    public Response addUserToUserms(Customer customer) {
        Log.info("Posting customer to another service");
        return httpClient.addCustomer(customer);
    }
}
