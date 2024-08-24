package org.acme;

import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("client-customer")
@Produces(APPLICATION_JSON)
public class ClientCustomerResource {

    private ProgrammaticHttpClientCustomer httpClientCustomer;


    public ClientCustomerResource() throws MalformedURLException {
        httpClientCustomer = RestClientBuilder.newBuilder()
                .baseUrl(new URL("http://localhost:8081"))
                .connectTimeout(500, TimeUnit.MILLISECONDS)
                .readTimeout(1200, TimeUnit.MILLISECONDS)
                .build(ProgrammaticHttpClientCustomer.class);
    }

    @GET
    @Path("/customers")
    public List<Customer> getPersonFromServiceDatabase() {
        Log.info("Getting all customers from service-database");
        return httpClientCustomer.getCustomersFromServiceDatabase();
    }

    @POST
    @Path("/persons")
    public Response addPersonFromServiceDatabase(Customer customer) {
        Log.info("Adding customers to service-database");
        return httpClientCustomer.addCustomerFromServiceDatabase(customer);
    }
}
